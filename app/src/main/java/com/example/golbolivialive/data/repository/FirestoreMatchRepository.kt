package com.example.golbolivialive.data.repository

import android.util.Log
import com.example.golbolivialive.data.datasource.BolivianFootballData
import com.example.golbolivialive.data.model.Club
import com.example.golbolivialive.data.model.LiveMatch
import com.example.golbolivialive.data.model.SignalSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class FirestoreMatchRepository {

    private val _matches = MutableStateFlow<List<LiveMatch>>(BolivianFootballData.getRoundMatches())
    val matches: StateFlow<List<LiveMatch>> = _matches.asStateFlow()

    private val _selectedMatch = MutableStateFlow<LiveMatch>(BolivianFootballData.getDefaultLiveMatch())
    val selectedMatch: StateFlow<LiveMatch> = _selectedMatch.asStateFlow()

    private val _isSyncing = MutableStateFlow(false)
    val isSyncing: StateFlow<Boolean> = _isSyncing.asStateFlow()

    fun selectMatch(match: LiveMatch) {
        _selectedMatch.value = match
    }

    suspend fun syncFromFirestore() {
        _isSyncing.value = true
        withContext(Dispatchers.IO) {
            try {
                // Try fetching liveEvents from local proxy endpoint (10.0.2.2 for Android emulator, 127.0.0.1 for local)
                val endpoints = listOf(
                    "http://10.0.2.2:3000/api/live-events",
                    "http://127.0.0.1:3000/api/live-events",
                    "http://localhost:3000/api/live-events"
                )

                var responseJson: String? = null
                for (urlStr in endpoints) {
                    try {
                        val url = URL(urlStr)
                        val conn = (url.openConnection() as HttpURLConnection).apply {
                            connectTimeout = 3000
                            readTimeout = 3000
                            requestMethod = "GET"
                            setRequestProperty("Accept", "application/json")
                        }
                        if (conn.responseCode == 200) {
                            val reader = BufferedReader(InputStreamReader(conn.inputStream))
                            responseJson = reader.readText()
                            reader.close()
                            conn.disconnect()
                            if (!responseJson.isNullOrBlank()) break
                        }
                    } catch (e: Exception) {
                        // try next endpoint
                    }
                }

                if (!responseJson.isNullOrBlank()) {
                    val parsedMatches = parseLiveEventsJson(responseJson)
                    if (parsedMatches.isNotEmpty()) {
                        _matches.value = parsedMatches
                        // Update current selected match if it was in the list
                        val currentId = _selectedMatch.value.id
                        val updatedCurrent = parsedMatches.find { it.id == currentId } ?: parsedMatches.first()
                        _selectedMatch.value = updatedCurrent
                    }
                }
            } catch (e: Exception) {
                Log.w("FirestoreMatchRepo", "Error syncing with Firestore: ${e.message}")
            } finally {
                _isSyncing.value = false
            }
        }
    }

    private fun parseLiveEventsJson(jsonStr: String): List<LiveMatch> {
        val list = mutableListOf<LiveMatch>()
        try {
            val jsonArray = JSONArray(jsonStr)
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val id = obj.optString("id", "match_$i")
                val title = obj.optString("title", "Partido en Vivo")
                val homeTeamId = obj.optString("homeTeam", "bolivar")
                val awayTeamId = obj.optString("awayTeam", "strongest")
                val isLive = obj.optBoolean("isLive", true)
                val homeScore = obj.optInt("homeScore", 0)
                val awayScore = obj.optInt("awayScore", 0)
                val matchMinute = obj.optInt("matchMinute", 0)
                val period = obj.optString("period", if (isLive) "1T" else "Finalizado")
                val tournament = obj.optString("tournamentName", "División Profesional de Bolivia")
                val stadium = obj.optString("stadiumName", "Estadio Hernando Siles")
                val customVideoUrl = obj.optString("customVideoUrl", "")
                val backupVideoUrl = obj.optString("backupVideoUrl", "")

                val homeClub = BolivianFootballData.CLUBS[homeTeamId] ?: Club(
                    id = homeTeamId,
                    name = homeTeamId.replaceFirstChar { it.uppercase() },
                    shortName = homeTeamId.take(10).uppercase(),
                    city = "Bolivia",
                    primaryColorHex = "#38bdf8",
                    secondaryColorHex = "#1e3a8a",
                    badgeEmoji = "⚽",
                    stadium = stadium,
                    altitudeMeters = 2500
                )

                val awayClub = BolivianFootballData.CLUBS[awayTeamId] ?: Club(
                    id = awayTeamId,
                    name = awayTeamId.replaceFirstChar { it.uppercase() },
                    shortName = awayTeamId.take(10).uppercase(),
                    city = "Bolivia",
                    primaryColorHex = "#eab308",
                    secondaryColorHex = "#1e293b",
                    badgeEmoji = "⚽",
                    stadium = stadium,
                    altitudeMeters = 2500
                )

                val hlsPrincipal = if (customVideoUrl.isNotBlank() && customVideoUrl.startsWith("http")) {
                    customVideoUrl
                } else {
                    "https://live-clarosports-linear.amagi.tv/playlist.m3u8"
                }

                val hlsBackup = if (backupVideoUrl.isNotBlank() && backupVideoUrl.startsWith("http")) {
                    backupVideoUrl
                } else {
                    "https://rbmn-live.akamaized.net/hls/live/590964/BoRB-AT/master.m3u8"
                }

                list.add(
                    LiveMatch(
                        id = id,
                        title = title,
                        tournamentName = tournament,
                        homeClub = homeClub,
                        awayClub = awayClub,
                        homeScore = homeScore,
                        awayScore = awayScore,
                        matchMinute = matchMinute,
                        period = period,
                        isLive = isLive,
                        stadiumName = stadium,
                        altitudeMeters = homeClub.altitudeMeters,
                        activeSignal = SignalSource.PRINCIPAL_HD,
                        hlsUrlPrincipal = hlsPrincipal,
                        hlsUrlBackup = hlsBackup,
                        hlsUrlDemo = "https://amg01269-amg01269c1-fifa-intl-4066.playouts.now.amagi.tv/playlist/amg01269-fifaplusfast-fifaplus-fifaplusintl/playlist.m3u8",
                        connectedFans = 1200 + (i * 315)
                    )
                )
            }
        } catch (e: Exception) {
            Log.w("FirestoreMatchRepo", "JSON parse error: ${e.message}")
        }
        return if (list.isNotEmpty()) list else BolivianFootballData.getRoundMatches()
    }
}
