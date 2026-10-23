package com.example.golbolivialive.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.golbolivialive.data.model.LiveMatch
import com.example.golbolivialive.data.model.SignalSource
import com.example.golbolivialive.ui.theme.*

@Composable
fun ScoreboardHeader(
    match: LiveMatch,
    isReloading: Boolean,
    onSelectSignal: (SignalSource) -> Unit,
    onReloadSignal: () -> Unit,
    onOpenAudience: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "reload_rotate")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(StadiumCard)
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .testTag("scoreboard_header")
    ) {
        // Tournament & Stadium info row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Icon(
                    imageVector = Icons.Default.SportsSoccer,
                    contentDescription = null,
                    tint = EmeraldLive,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = match.tournamentName,
                    color = SlateTextSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Audience counter button
            Surface(
                color = StadiumCardElevated,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .clickable { onOpenAudience() }
                    .testTag("audience_counter_chip")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(EmeraldLive)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "${match.connectedFans} hinchas",
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Big Match Scoreboard
        Card(
            colors = CardDefaults.cardColors(containerColor = StadiumDark.copy(alpha = 0.8f)),
            shape = RoundedCornerShape(14.dp),
            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(
                listOf(ElectricSky.copy(alpha = 0.4f), EmeraldLive.copy(alpha = 0.4f))
            )),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Home Team
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = match.homeClub.badgeEmoji,
                            fontSize = 32.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = match.homeClub.shortName,
                            color = SlateTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                        Text(
                            text = match.homeClub.city,
                            color = SlateTextMuted,
                            fontSize = 10.sp
                        )
                    }

                    // Score and Period Box
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${match.homeScore}",
                                color = SlateTextPrimary,
                                fontSize = 34.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Text(
                                text = " : ",
                                color = SlateTextMuted,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 4.dp)
                            )
                            Text(
                                text = "${match.awayScore}",
                                color = SlateTextPrimary,
                                fontSize = 34.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }

                        Surface(
                            color = EmeraldLive.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(6.dp),
                            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(
                                listOf(EmeraldLive.copy(alpha = 0.6f), EmeraldLive.copy(alpha = 0.6f))
                            ))
                        ) {
                            Text(
                                text = "${match.matchMinute}' • ${match.period}",
                                color = EmeraldLive,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }

                    // Away Team
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = match.awayClub.badgeEmoji,
                            fontSize = 32.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = match.awayClub.shortName,
                            color = SlateTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            maxLines = 1
                        )
                        Text(
                            text = match.awayClub.city,
                            color = SlateTextMuted,
                            fontSize = 10.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Stadium & Altitude Indicator
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = ElectricSkyLight,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = "${match.stadiumName} • ${match.altitudeMeters} msnm",
                        color = SlateTextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Official Announcement Banner (if available)
        if (match.officialAnnouncement.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = ElectricSky.copy(alpha = 0.15f),
                shape = RoundedCornerShape(8.dp),
                border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(
                    listOf(ElectricSky.copy(alpha = 0.4f), ElectricSky.copy(alpha = 0.4f))
                )),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = null,
                        tint = ElectricSkyLight,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = match.officialAnnouncement,
                        color = SlateTextPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Signal Selector Pills Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.weight(1f)
            ) {
                SignalSource.values().forEach { signal ->
                    val isSelected = match.activeSignal == signal
                    val bg = if (isSelected) EmeraldLive else StadiumCardElevated
                    val textColor = if (isSelected) StadiumDark else SlateTextSecondary
                    val borderColor = if (isSelected) EmeraldGlow else StadiumBorder

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(bg)
                            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
                            .clickable { onSelectSignal(signal) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .testTag("signal_${signal.name.lowercase()}")
                    ) {
                        Text(
                            text = signal.label,
                            color = textColor,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }

            // Reload Signal Button with Animation
            IconButton(
                onClick = onReloadSignal,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(StadiumCardElevated)
                    .testTag("signal_reload_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Recargar Señal",
                    tint = if (isReloading) EmeraldLive else SlateTextPrimary,
                    modifier = Modifier
                        .size(18.dp)
                        .then(if (isReloading) Modifier.rotate(rotation) else Modifier)
                )
            }
        }
    }
}
