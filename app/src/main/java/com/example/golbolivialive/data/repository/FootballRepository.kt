package com.example.golbolivialive.data.repository

import com.example.golbolivialive.data.datasource.BolivianFootballData
import com.example.golbolivialive.data.datasource.IptvCatalogData
import com.example.golbolivialive.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.*

class FootballRepository {

    private val firestoreRepo = FirestoreMatchRepository()
    val allMatches: StateFlow<List<LiveMatch>> = firestoreRepo.matches
    val isSyncingFirestore: StateFlow<Boolean> = firestoreRepo.isSyncing

    private val _liveMatch = MutableStateFlow(BolivianFootballData.getDefaultLiveMatch())
    val liveMatch: StateFlow<LiveMatch> = _liveMatch.asStateFlow()

    private val _chatMessages = MutableStateFlow(BolivianFootballData.INITIAL_CHAT_MESSAGES)
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _livePoll = MutableStateFlow(BolivianFootballData.DEFAULT_POLL)
    val livePoll: StateFlow<LivePoll> = _livePoll.asStateFlow()

    private val _iptvChannels = MutableStateFlow(IptvCatalogData.CHANNELS)
    val iptvChannels: StateFlow<List<IptvChannel>> = _iptvChannels.asStateFlow()

    private val _activeIptvChannel = MutableStateFlow<IptvChannel?>(null)
    val activeIptvChannel: StateFlow<IptvChannel?> = _activeIptvChannel.asStateFlow()

    private val _favoriteChannelIds = MutableStateFlow(setOf("claro-sports-latam", "redbull-tv-live", "fifa-plus-es"))
    val favoriteChannelIds: StateFlow<Set<String>> = _favoriteChannelIds.asStateFlow()

    suspend fun syncFirestore() {
        firestoreRepo.syncFromFirestore()
        // If current match exists in new matches, keep it updated
        val updated = firestoreRepo.matches.value.find { it.id == _liveMatch.value.id }
        if (updated != null) {
            _liveMatch.value = updated
        }
    }

    fun selectMatch(match: LiveMatch) {
        firestoreRepo.selectMatch(match)
        _liveMatch.value = match
    }

    fun switchMatchSignal(signal: SignalSource) {
        _liveMatch.update { it.copy(activeSignal = signal) }
    }

    fun reloadSignal() {
        // Triggers player reload by resetting or re-notifying flow
        val current = _liveMatch.value
        _liveMatch.value = current.copy()
    }

    fun sendChatMessage(sender: String, clubId: String, text: String, superChatAmount: Int? = null) {
        if (text.isBlank()) return
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timestamp = timeFormat.format(Date())
        val newMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            sender = sender.ifBlank { "Hincha_Boliviano" },
            clubId = clubId,
            text = text.trim(),
            timestamp = timestamp,
            superChatAmount = superChatAmount
        )
        _chatMessages.update { listOf(newMsg) + it }
    }

    fun votePoll(optionId: String) {
        _livePoll.update { current ->
            if (current.userVotedId != null) return@update current // already voted
            val updatedOptions = current.options.map { opt ->
                if (opt.id == optionId) opt.copy(votes = opt.votes + 1) else opt
            }
            current.copy(
                options = updatedOptions,
                totalVotes = current.totalVotes + 1,
                userVotedId = optionId
            )
        }
    }

    fun toggleFavorite(channelId: String) {
        _favoriteChannelIds.update { favs ->
            if (favs.contains(channelId)) favs - channelId else favs + channelId
        }
    }

    fun selectIptvChannel(channel: IptvChannel?) {
        _activeIptvChannel.value = channel
    }
}
