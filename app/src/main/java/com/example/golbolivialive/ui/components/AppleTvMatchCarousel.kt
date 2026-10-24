package com.example.golbolivialive.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.golbolivialive.data.model.LiveMatch
import com.example.golbolivialive.ui.theme.EmeraldLive

@Composable
fun AppleTvMatchCarousel(
    matches: List<LiveMatch>,
    selectedMatchId: String,
    isSyncing: Boolean,
    onSelectMatch: (LiveMatch) -> Unit,
    onSyncNow: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .testTag("apple_tv_match_carousel")
    ) {
        // Section Header (Apple TV style title & live counter)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Tv,
                    contentDescription = null,
                    tint = EmeraldLive,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "PARTIDOS EN VIVO & DESTACADOS",
                    color = Color.White,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.5.sp
                )
                Surface(
                    color = EmeraldLive.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(6.dp)
                ) {
                    Text(
                        text = "${matches.size}",
                        color = EmeraldLive,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            // Sync with Firestore button
            IconButton(
                onClick = onSyncNow,
                modifier = Modifier.size(32.dp).testTag("sync_firestore_button")
            ) {
                if (isSyncing) {
                    CircularProgressIndicator(
                        color = EmeraldLive,
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(16.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Sincronizar Firestore",
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Horizontal Carousel
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(matches, key = { it.id }) { match ->
                AppleTvMatchCard(
                    match = match,
                    isSelected = match.id == selectedMatchId,
                    onClick = { onSelectMatch(match) }
                )
            }
        }
    }
}
