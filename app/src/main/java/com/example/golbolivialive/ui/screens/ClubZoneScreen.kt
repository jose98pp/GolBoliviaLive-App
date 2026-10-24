package com.example.golbolivialive.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.golbolivialive.data.datasource.BolivianFootballData
import com.example.golbolivialive.data.model.Club
import com.example.golbolivialive.data.model.ExclusiveContent
import com.example.golbolivialive.data.model.StandingTeam
import com.example.golbolivialive.ui.theme.*
import com.example.golbolivialive.ui.viewmodel.FootballViewModel

@Composable
fun ClubZoneScreen(
    viewModel: FootballViewModel,
    modifier: Modifier = Modifier
) {
    val selectedClubId by viewModel.selectedClubId.collectAsState()
    val allClubs = BolivianFootballData.CLUBS.values.toList()
    val selectedClub = BolivianFootballData.CLUBS[selectedClubId] ?: allClubs.first()

    var activeSubTab by remember { mutableStateOf(0) } // 0: Posiciones, 1: Exclusivo

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(StadiumDark)
            .testTag("club_zone_screen")
    ) {
        // Club Selection Horizontal Carousel
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(StadiumCard)
                .padding(vertical = 12.dp)
        ) {
            Text(
                text = "Clubes de la División Profesional",
                color = SlateTextSecondary,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(6.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 14.dp)
            ) {
                items(allClubs) { club ->
                    val isSelected = club.id == selectedClub.id
                    val borderColor = if (isSelected) EmeraldLive else StadiumBorder

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) StadiumCardElevated else StadiumDark)
                            .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
                            .clickable { viewModel.selectClub(club.id) }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .testTag("club_chip_${club.id}")
                    ) {
                        Text(
                            text = club.badgeEmoji,
                            fontSize = 26.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = club.shortName,
                            color = if (isSelected) EmeraldLive else SlateTextPrimary,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // Club Detail Card
        Card(
            colors = CardDefaults.cardColors(containerColor = StadiumCardElevated),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = selectedClub.badgeEmoji, fontSize = 34.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = selectedClub.name,
                                color = SlateTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "${selectedClub.city}, Bolivia",
                                color = EmeraldGlow,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Surface(
                        color = ElectricSky.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, tint = ElectricSkyLight, modifier = Modifier.size(13.dp))
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "${selectedClub.altitudeMeters}m",
                                color = ElectricSkyLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Stadium, contentDescription = null, tint = SlateTextSecondary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Estadio: ${selectedClub.stadium}",
                        color = SlateTextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // Subtabs: Standings & Exclusive Media
        TabRow(
            selectedTabIndex = activeSubTab,
            containerColor = StadiumCard,
            contentColor = EmeraldLive,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[activeSubTab]),
                    color = EmeraldLive
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Tab(
                selected = activeSubTab == 0,
                onClick = { activeSubTab = 0 },
                text = { Text("Tabla de Posiciones", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                icon = { Icon(Icons.Default.FormatListNumbered, contentDescription = null, modifier = Modifier.size(16.dp)) }
            )
            Tab(
                selected = activeSubTab == 1,
                onClick = { activeSubTab = 1 },
                text = { Text("Zona Exclusiva", fontWeight = FontWeight.Bold, fontSize = 12.sp) },
                icon = { Icon(Icons.Default.VideoLibrary, contentDescription = null, modifier = Modifier.size(16.dp)) }
            )
        }

        // Subtab Content
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            when (activeSubTab) {
                0 -> StandingsTableView(BolivianFootballData.STANDINGS)
                1 -> ExclusiveContentView(BolivianFootballData.EXCLUSIVE_CONTENT, onUnlock = { viewModel.openVipModal() })
            }
        }
    }
}

@Composable
fun StandingsTableView(standings: List<StandingTeam>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp, vertical = 10.dp)
            .testTag("standings_table"),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("#", color = SlateTextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(24.dp))
                Text("Equipo", color = SlateTextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text("PJ", color = SlateTextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(30.dp), textAlign = TextAlign.Center)
                Text("DG", color = SlateTextMuted, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(32.dp), textAlign = TextAlign.Center)
                Text("PTS", color = EmeraldLive, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.width(36.dp), textAlign = TextAlign.Center)
            }
        }

        items(standings) { team ->
            val club = BolivianFootballData.CLUBS[team.clubId]
            val isTopZone = team.position <= 4

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (isTopZone) StadiumCardElevated else StadiumCard
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${team.position}",
                        color = if (isTopZone) EmeraldLive else SlateTextMuted,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(24.dp)
                    )

                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = club?.badgeEmoji ?: "⚽", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = team.name,
                            color = SlateTextPrimary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Text(
                        text = "${team.played}",
                        color = SlateTextSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.width(30.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "${team.goalDiff}",
                        color = if (team.goalDiff > 0) EmeraldGlow else if (team.goalDiff < 0) CrimsonAlert else SlateTextSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.width(32.dp),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "${team.points}",
                        color = EmeraldLive,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 13.sp,
                        modifier = Modifier.width(36.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun ExclusiveContentView(
    contentList: List<ExclusiveContent>,
    onUnlock: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
            .testTag("exclusive_content_list"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(contentList) { item ->
            val club = BolivianFootballData.CLUBS[item.clubId]

            Card(
                colors = CardDefaults.cardColors(containerColor = StadiumCard),
                shape = RoundedCornerShape(12.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    brush = androidx.compose.ui.graphics.SolidColor(
                        if (item.isLocked) GoldStar.copy(alpha = 0.5f) else StadiumBorder
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { if (item.isLocked) onUnlock() }
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = StadiumCardElevated,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "${club?.badgeEmoji ?: "⚽"} ${item.category.uppercase()}",
                                color = EmeraldLive,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (item.isLocked) {
                                Surface(
                                    color = GoldStar,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Icon(Icons.Default.Lock, contentDescription = null, tint = StadiumDark, modifier = Modifier.size(11.dp))
                                        Spacer(modifier = Modifier.width(3.dp))
                                        Text("VIP", color = StadiumDark, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                                    }
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                            }
                            Text(text = item.duration, color = SlateTextMuted, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = item.title,
                        color = SlateTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.description,
                        color = SlateTextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${item.views} visualizaciones",
                            color = SlateTextMuted,
                            fontSize = 11.sp
                        )

                        if (item.isLocked) {
                            Text(
                                text = "Desbloquear con VIP →",
                                color = GoldStar,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        } else {
                            Text(
                                text = "Reproducir Clip ▶",
                                color = EmeraldLive,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
