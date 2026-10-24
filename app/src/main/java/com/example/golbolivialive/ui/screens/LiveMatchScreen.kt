package com.example.golbolivialive.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.golbolivialive.data.model.SignalSource
import com.example.golbolivialive.ui.components.*
import com.example.golbolivialive.ui.theme.*
import com.example.golbolivialive.ui.viewmodel.FootballViewModel

@Composable
fun LiveMatchScreen(
    viewModel: FootballViewModel,
    modifier: Modifier = Modifier
) {
    val match by viewModel.liveMatch.collectAsState()
    val allMatches by viewModel.allMatches.collectAsState()
    val isSyncingFirestore by viewModel.isSyncingFirestore.collectAsState()
    val chatMessages by viewModel.chatMessages.collectAsState()
    val livePoll by viewModel.livePoll.collectAsState()
    val isReloading by viewModel.isReloadingSignal.collectAsState()
    val isAudienceModalOpen by viewModel.isAudienceModalOpen.collectAsState()

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(
        "Chat" to Icons.Default.ChatBubble,
        "Estadísticas" to Icons.Default.BarChart,
        "Incidencias" to Icons.Default.Timer,
        "Encuesta" to Icons.Default.Poll
    )

    // Current HLS stream url depending on selected signal
    val currentStreamUrl = when (match.activeSignal) {
        SignalSource.PRINCIPAL_HD -> match.hlsUrlPrincipal
        SignalSource.BACKUP -> match.hlsUrlBackup
        SignalSource.RADIO_DEMO -> match.hlsUrlDemo
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(StadiumDark)
            .testTag("live_match_screen")
    ) {
        // Video Player Surface (Apple TV Spotlight Hero)
        VideoPlayerSurface(
            streamUrl = currentStreamUrl,
            signalTitle = "${match.activeSignal.label} (${match.activeSignal.badge})",
            homeClubShortName = match.homeClub.shortName,
            awayClubShortName = match.awayClub.shortName,
            isLive = match.isLive,
            onReload = { viewModel.reloadSignal() }
        )

        // Scoreboard & Signals Header
        ScoreboardHeader(
            match = match,
            isReloading = isReloading,
            onSelectSignal = { viewModel.switchMatchSignal(it) },
            onReloadSignal = { viewModel.reloadSignal() },
            onOpenAudience = { viewModel.openAudienceModal() }
        )

        // Apple TV Style Horizontal Match Carousel (Dual-color club cards)
        AppleTvMatchCarousel(
            matches = allMatches,
            selectedMatchId = match.id,
            isSyncing = isSyncingFirestore,
            onSelectMatch = { selected -> viewModel.selectMatch(selected) },
            onSyncNow = { viewModel.syncFirestore() }
        )

        // Sub-navigation Tab Row (Chat, Stats, Events, Poll)
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = StadiumCardElevated,
            contentColor = EmeraldLive,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = EmeraldLive
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, (label, icon) ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium
                        )
                    },
                    icon = { Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp)) },
                    selectedContentColor = EmeraldLive,
                    unselectedContentColor = SlateTextSecondary,
                    modifier = Modifier.testTag("match_tab_$index")
                )
            }
        }

        // Selected Tab Content Area
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            when (selectedTabIndex) {
                0 -> LiveChatTab(
                    messages = chatMessages,
                    onSendMessage = { sender, clubId, text, superChat ->
                        viewModel.sendChatMessage(sender, clubId, text, superChat)
                    }
                )
                1 -> MatchStatsTab(
                    stats = match.stats,
                    homeTeam = match.homeClub,
                    awayTeam = match.awayClub
                )
                2 -> MatchEventsTab(events = match.events)
                3 -> LivePollTab(
                    poll = livePoll,
                    onVote = { optionId -> viewModel.votePoll(optionId) }
                )
            }
        }
    }

    // Audience Modal Dialog
    if (isAudienceModalOpen) {
        AudienceBreakdownDialog(
            totalFans = match.connectedFans,
            onDismiss = { viewModel.closeAudienceModal() }
        )
    }
}

@Composable
fun AudienceBreakdownDialog(
    totalFans: Int,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.People, contentDescription = null, tint = EmeraldLive)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Audiencia en Vivo 🇧🇴", color = SlateTextPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        },
        text = {
            Column {
                Text(
                    text = "$totalFans hinchas sintonizando la señal de GolBolivia en directo.",
                    color = SlateTextSecondary,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(14.dp))

                val departments = listOf(
                    "La Paz & El Alto" to "42%",
                    "Santa Cruz" to "28%",
                    "Cochabamba" to "16%",
                    "Oruro & Potosí" to "7%",
                    "Tarija & Chuquisaca" to "4%",
                    "Exterior (Bolivianos en el Mundo)" to "3%"
                )

                departments.forEach { (dept, pct) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(dept, color = SlateTextPrimary, fontSize = 12.sp)
                        Text(pct, color = EmeraldLive, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldLive)
            ) {
                Text("Entendido", color = StadiumDark, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = StadiumCardElevated
    )
}
