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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.golbolivialive.data.model.IPTV_COUNTRIES_LIST
import com.example.golbolivialive.data.model.IptvChannel
import com.example.golbolivialive.ui.components.VideoPlayerSurface
import com.example.golbolivialive.ui.theme.*
import com.example.golbolivialive.ui.viewmodel.FootballViewModel

@Composable
fun IptvChannelsScreen(
    viewModel: FootballViewModel,
    modifier: Modifier = Modifier
) {
    val channels by viewModel.filteredChannels.collectAsState()
    val activeChannel by viewModel.activeIptvChannel.collectAsState()
    val selectedCountry by viewModel.selectedIptvCountry.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val favoriteIds by viewModel.favoriteChannelIds.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(StadiumDark)
            .testTag("iptv_channels_screen")
    ) {
        // If an IPTV channel is active, render the dedicated player surface
        if (activeChannel != null) {
            Column {
                VideoPlayerSurface(
                    streamUrl = activeChannel!!.streamUrl,
                    signalTitle = "${activeChannel!!.name} (${activeChannel!!.resolution})",
                    isLive = true,
                    onReload = { viewModel.reloadSignal() }
                )
                // Channel Info & Close bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(StadiumCardElevated)
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = activeChannel!!.name,
                            color = SlateTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "${activeChannel!!.group} • ${activeChannel!!.countryName}",
                            color = EmeraldGlow,
                            fontSize = 11.sp
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(
                            onClick = { viewModel.toggleFavorite(activeChannel!!.id) },
                            modifier = Modifier.size(36.dp)
                        ) {
                            val isFav = favoriteIds.contains(activeChannel!!.id)
                            Icon(
                                imageVector = if (isFav) Icons.Default.Star else Icons.Default.StarBorder,
                                contentDescription = "Favorito",
                                tint = if (isFav) GoldStar else SlateTextMuted
                            )
                        }

                        IconButton(
                            onClick = { viewModel.closeIptvChannel() },
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar Canal",
                                tint = SlateTextPrimary
                            )
                        }
                    }
                }
            }
        }

        // Search and Filters Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(StadiumCard)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Tv,
                        contentDescription = null,
                        tint = EmeraldLive,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Canales de Deportes",
                        color = SlateTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

                Surface(
                    color = StadiumCardElevated,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "${channels.size} señales",
                        color = SlateTextSecondary,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Search Box
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                placeholder = { Text("Buscar canal deportivo...", color = SlateTextMuted, fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = SlateTextMuted) },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = StadiumDark,
                    unfocusedContainerColor = StadiumDark,
                    focusedBorderColor = EmeraldLive,
                    unfocusedBorderColor = StadiumBorder,
                    focusedTextColor = SlateTextPrimary,
                    unfocusedTextColor = SlateTextPrimary
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("iptv_search_input")
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Country Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(IPTV_COUNTRIES_LIST) { country ->
                    val isSelected = selectedCountry == country.code
                    val bg = if (isSelected) EmeraldLive else StadiumCardElevated
                    val textColor = if (isSelected) StadiumDark else SlateTextSecondary
                    val borderColor = if (isSelected) EmeraldGlow else StadiumBorder

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(bg)
                            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
                            .clickable { viewModel.setIptvCountry(country.code) }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .testTag("iptv_country_${country.code.lowercase()}")
                    ) {
                        Text(
                            text = "${country.flagEmoji} ${country.label}",
                            color = textColor,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                }
            }
        }

        // Channels List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp, vertical = 10.dp)
                .testTag("iptv_channels_list"),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(channels, key = { it.id }) { channel ->
                val isPlaying = activeChannel?.id == channel.id
                val isFav = favoriteIds.contains(channel.id)

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isPlaying) EmeraldLive.copy(alpha = 0.15f) else StadiumCard
                    ),
                    shape = RoundedCornerShape(12.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(
                            if (isPlaying) EmeraldLive else StadiumBorder
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.playIptvChannel(channel) }
                        .testTag("channel_card_${channel.id}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Channel Logo
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = channel.logoUrl,
                                contentDescription = channel.name,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        // Details
                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = channel.name,
                                    color = SlateTextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "${channel.group} • ${channel.countryName}",
                                color = SlateTextSecondary,
                                fontSize = 11.sp
                            )
                            if (channel.notes.isNotBlank()) {
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = channel.notes,
                                    color = SlateTextMuted,
                                    fontSize = 10.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                        // Resolution Badge & Play / Fav actions
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Surface(
                                color = if (isPlaying) EmeraldLive else ElectricSky.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = if (isPlaying) "REPRODUCIENDO" else channel.resolution,
                                    color = if (isPlaying) StadiumDark else ElectricSkyLight,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }

                            IconButton(
                                onClick = { viewModel.toggleFavorite(channel.id) },
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(
                                    imageVector = if (isFav) Icons.Default.Star else Icons.Default.StarBorder,
                                    contentDescription = "Favorito",
                                    tint = if (isFav) GoldStar else SlateTextMuted,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
