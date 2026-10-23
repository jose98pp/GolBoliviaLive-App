package com.example.golbolivialive.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.golbolivialive.data.datasource.BolivianFootballData
import com.example.golbolivialive.data.model.*
import com.example.golbolivialive.ui.theme.*

// ── 1. LIVE CHAT TAB ──

@Composable
fun LiveChatTab(
    messages: List<ChatMessage>,
    onSendMessage: (sender: String, clubId: String, text: String, superChat: Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    var textInput by remember { mutableStateOf("") }
    var senderName by remember { mutableStateOf("") }
    var selectedClub by remember { mutableStateOf("bolivar") }
    var superChatDialogOpen by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(StadiumDark)
            .testTag("live_chat_tab")
    ) {
        // Quick reaction bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(StadiumCard)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Reacciones:",
                color = SlateTextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            listOf("⚽", "🔥", "🇧🇴", "👏", "⚡", "🐯").forEach { emoji ->
                Text(
                    text = emoji,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            onSendMessage(senderName.ifBlank { "Hincha" }, selectedClub, emoji, null)
                        }
                        .padding(4.dp)
                )
            }
        }

        // Chat messages stream
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            reverseLayout = true,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                val club = BolivianFootballData.CLUBS[msg.clubId]
                val isSuperChat = msg.superChatAmount != null && msg.superChatAmount > 0

                val cardBg = if (isSuperChat) GoldStar.copy(alpha = 0.18f) else StadiumCard
                val cardBorder = if (isSuperChat) GoldStar.copy(alpha = 0.5f) else StadiumBorder

                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = cardBg),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(cardBorder)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = club?.badgeEmoji ?: "⚽",
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = msg.sender,
                                    color = if (msg.isOfficialRelator) EmeraldGlow else if (isSuperChat) GoldStar else SlateTextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )

                                if (msg.isOfficialRelator) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        color = EmeraldLive.copy(alpha = 0.25f),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = "RELATOR OFICIAL",
                                            color = EmeraldLive,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                        )
                                    }
                                } else if (msg.isVip) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Surface(
                                        color = GoldStar.copy(alpha = 0.2f),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = "VIP",
                                            color = GoldStar,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            Text(
                                text = msg.timestamp,
                                color = SlateTextMuted,
                                fontSize = 10.sp
                            )
                        }

                        if (isSuperChat) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Surface(
                                color = GoldStar,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = "SUPER CHAT Bs. ${msg.superChatAmount}",
                                    color = StadiumDark,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = msg.text,
                            color = SlateTextPrimary,
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }
        }

        // Bottom text input controls
        Surface(
            color = StadiumCard,
            tonalElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Club badge picker toggle
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(StadiumCardElevated)
                        .clickable {
                            // Cycle through major Bolivian clubs
                            val clubKeys = listOf("bolivar", "strongest", "wilstermann", "oriente", "blooming", "always")
                            val nextIndex = (clubKeys.indexOf(selectedClub) + 1) % clubKeys.size
                            selectedClub = clubKeys[nextIndex]
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = BolivianFootballData.CLUBS[selectedClub]?.badgeEmoji ?: "⚡",
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Input Field
                OutlinedTextField(
                    value = textInput,
                    onValueChange = { textInput = it },
                    placeholder = { Text("Escribe en el chat...", color = SlateTextMuted, fontSize = 13.sp) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = StadiumCardElevated,
                        unfocusedContainerColor = StadiumCardElevated,
                        focusedBorderColor = EmeraldLive,
                        unfocusedBorderColor = StadiumBorder,
                        focusedTextColor = SlateTextPrimary,
                        unfocusedTextColor = SlateTextPrimary
                    ),
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = {
                        if (textInput.isNotBlank()) {
                            onSendMessage(senderName.ifBlank { "Hincha" }, selectedClub, textInput, null)
                            textInput = ""
                        }
                    }),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .testTag("chat_input_field")
                )

                Spacer(modifier = Modifier.width(6.dp))

                // SuperChat Donation Button
                IconButton(
                    onClick = { superChatDialogOpen = true },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(GoldStar.copy(alpha = 0.2f))
                        .testTag("chat_superchat_button")
                ) {
                    Text("Bs", color = GoldStar, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.width(4.dp))

                // Send Button
                IconButton(
                    onClick = {
                        if (textInput.isNotBlank()) {
                            onSendMessage(senderName.ifBlank { "Hincha" }, selectedClub, textInput, null)
                            textInput = ""
                        }
                    },
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(EmeraldLive)
                        .testTag("chat_send_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar",
                        tint = StadiumDark,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }

    // SuperChat Quick Modal
    if (superChatDialogOpen) {
        AlertDialog(
            onDismissRequest = { superChatDialogOpen = false },
            title = { Text("Apoyar con Super Chat ⚽🇧🇴", color = SlateTextPrimary, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text("Destaca tu mensaje en la transmisión oficial y apoya la cobertura del fútbol boliviano:", color = SlateTextSecondary, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf(5, 10, 20, 50).forEach { amount ->
                            Button(
                                onClick = {
                                    val msg = textInput.ifBlank { "¡Vamos por la victoria! Saludos a toda Bolivia" }
                                    onSendMessage(senderName.ifBlank { "Hincha_VIP" }, selectedClub, msg, amount)
                                    textInput = ""
                                    superChatDialogOpen = false
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = GoldStar),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Bs. $amount", color = StadiumDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                TextButton(onClick = { superChatDialogOpen = false }) {
                    Text("Cancelar", color = SlateTextMuted)
                }
            },
            containerColor = StadiumCardElevated
        )
    }
}

// ── 2. MATCH STATS TAB ──

@Composable
fun MatchStatsTab(
    stats: MatchStats,
    homeTeam: Club,
    awayTeam: Club,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(StadiumDark)
            .padding(14.dp)
            .testTag("match_stats_tab"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            // Team names header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${homeTeam.badgeEmoji} ${homeTeam.shortName}",
                    color = SlateTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = "ESTADÍSTICAS OFICIALES",
                    color = EmeraldLive,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
                Text(
                    text = "${awayTeam.shortName} ${awayTeam.badgeEmoji}",
                    color = SlateTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }

        item {
            StatComparisonRow("Posesión de Balón", "${stats.possessionHome}%", "${stats.possessionAway}%", stats.possessionHome, stats.possessionAway)
        }
        item {
            StatComparisonRow("Tiros Totales", "${stats.shotsHome}", "${stats.shotsAway}", stats.shotsHome, stats.shotsAway)
        }
        item {
            StatComparisonRow("Tiros al Arco", "${stats.shotsOnTargetHome}", "${stats.shotsOnTargetAway}", stats.shotsOnTargetHome, stats.shotsOnTargetAway)
        }
        item {
            StatComparisonRow("Tiros de Esquina", "${stats.cornersHome}", "${stats.cornersAway}", stats.cornersHome, stats.cornersAway)
        }
        item {
            StatComparisonRow("Faltas Cometidas", "${stats.foulsHome}", "${stats.foulsAway}", stats.foulsHome, stats.foulsAway)
        }
        item {
            StatComparisonRow("Tarjetas Amarillas", "${stats.yellowCardsHome}", "${stats.yellowCardsAway}", stats.yellowCardsHome, stats.yellowCardsAway)
        }
        item {
            StatComparisonRow("Precisión de Pases", "${stats.passAccuracyHome}%", "${stats.passAccuracyAway}%", stats.passAccuracyHome, stats.passAccuracyAway)
        }
    }
}

@Composable
fun StatComparisonRow(label: String, valHome: String, valAway: String, numHome: Int, numAway: Int) {
    val total = (numHome + numAway).coerceAtLeast(1).toFloat()
    val homeWeight = (numHome / total).coerceIn(0.1f, 0.9f)
    val awayWeight = 1f - homeWeight

    Card(
        colors = CardDefaults.cardColors(containerColor = StadiumCard),
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = valHome, color = ElectricSkyLight, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = label, color = SlateTextSecondary, fontWeight = FontWeight.Medium, fontSize = 12.sp)
                Text(text = valAway, color = GoldStar, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(StadiumCardElevated)
            ) {
                Box(
                    modifier = Modifier
                        .weight(homeWeight)
                        .fillMaxHeight()
                        .background(ElectricSky)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Box(
                    modifier = Modifier
                        .weight(awayWeight)
                        .fillMaxHeight()
                        .background(GoldStar)
                )
            }
        }
    }
}

// ── 3. MATCH EVENTS TAB ──

@Composable
fun MatchEventsTab(
    events: List<MatchEvent>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(StadiumDark)
            .padding(14.dp)
            .testTag("match_events_tab"),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(events) { ev ->
            val icon = when (ev.type) {
                EventType.GOAL -> "⚽"
                EventType.YELLOW_CARD -> "🟨"
                EventType.RED_CARD -> "🟥"
                EventType.SUBSTITUTION -> "🔄"
                EventType.VAR -> "📺"
                EventType.COMMENTARY -> "🎙️"
                EventType.CORNER -> "🚩"
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = StadiumCard),
                shape = RoundedCornerShape(10.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(
                        if (ev.type == EventType.GOAL) EmeraldLive.copy(alpha = 0.5f) else StadiumBorder
                    )
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = StadiumCardElevated,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(38.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "${ev.minute}'", color = EmeraldLive, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(text = icon, fontSize = 20.sp)

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        if (ev.player != null) {
                            Text(
                                text = ev.player,
                                color = SlateTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                        Text(
                            text = ev.description,
                            color = SlateTextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 16.sp
                        )
                    }

                    if (ev.scoreAfter != null) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            color = EmeraldLive.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(
                                text = ev.scoreAfter,
                                color = EmeraldLive,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 13.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// ── 4. LIVE POLL TAB ──

@Composable
fun LivePollTab(
    poll: LivePoll,
    onVote: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(StadiumDark)
            .padding(16.dp)
            .testTag("live_poll_tab")
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = StadiumCard),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Poll, contentDescription = null, tint = EmeraldLive, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "ENCUESTA EN VIVO", color = EmeraldLive, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                    Text(text = "${poll.totalVotes} votos", color = SlateTextMuted, fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = poll.question,
                    color = SlateTextPrimary,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                poll.options.forEach { opt ->
                    val percent = if (poll.totalVotes > 0) ((opt.votes.toFloat() / poll.totalVotes) * 100).toInt() else 0
                    val isVoted = poll.userVotedId == opt.id

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isVoted) EmeraldLive.copy(alpha = 0.15f) else StadiumCardElevated)
                            .clickable(enabled = poll.userVotedId == null) { onVote(opt.id) }
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = opt.text,
                                color = if (isVoted) EmeraldLive else SlateTextPrimary,
                                fontWeight = if (isVoted) FontWeight.Bold else FontWeight.Medium,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "$percent%",
                                color = if (isVoted) EmeraldLive else SlateTextSecondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { percent / 100f },
                            color = if (isVoted) EmeraldLive else ElectricSkyLight,
                            trackColor = StadiumBorder,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp))
                        )
                    }
                }

                if (poll.userVotedId != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "✓ ¡Tu voto ha sido registrado!",
                        color = EmeraldLive,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
