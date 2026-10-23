package com.example.golbolivialive

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.golbolivialive.ui.screens.ClubZoneScreen
import com.example.golbolivialive.ui.screens.IptvChannelsScreen
import com.example.golbolivialive.ui.screens.LiveMatchScreen
import com.example.golbolivialive.ui.screens.VipDonationDialog
import com.example.golbolivialive.ui.theme.*
import com.example.golbolivialive.ui.viewmodel.FootballViewModel

enum class NavigationTab(val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    LIVE_MATCH("En Vivo", Icons.Default.SportsSoccer),
    IPTV_CHANNELS("Canales TV", Icons.Default.Tv),
    CLUBS_ZONE("Zona Clubes", Icons.Default.Shield),
    VIP_DONATION("Donar VIP", Icons.Default.WorkspacePremium)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GolBoliviaApp(
    viewModel: FootballViewModel = viewModel()
) {
    var currentTab by remember { mutableStateOf(NavigationTab.LIVE_MATCH) }
    val isVipModalOpen by viewModel.isVipModalOpen.collectAsState()

    // Handle back button for secondary screens
    if (currentTab != NavigationTab.LIVE_MATCH) {
        BackHandler {
            currentTab = NavigationTab.LIVE_MATCH
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(
                            color = EmeraldLive,
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text("⚽", fontSize = 16.sp)
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "GolBolivia",
                                    fontWeight = FontWeight.ExtraBold,
                                    color = SlateTextPrimary,
                                    fontSize = 17.sp
                                )
                                Text(
                                    text = "LIVE",
                                    fontWeight = FontWeight.Black,
                                    color = EmeraldLive,
                                    fontSize = 17.sp,
                                    modifier = Modifier.padding(start = 3.dp)
                                )
                            }
                            Text(
                                text = "Fútbol Boliviano en Directo",
                                color = SlateTextSecondary,
                                fontSize = 10.sp
                            )
                        }
                    }
                },
                actions = {
                    // VIP Badge Button
                    Surface(
                        color = GoldStar.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .clickable { viewModel.openVipModal() }
                            .padding(end = 8.dp)
                            .testTag("topbar_vip_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.WorkspacePremium,
                                contentDescription = null,
                                tint = GoldStar,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "VIP / Donar",
                                color = GoldStar,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = StadiumDark,
                    titleContentColor = SlateTextPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = StadiumCard,
                contentColor = EmeraldLive,
                windowInsets = WindowInsets.navigationBars,
                modifier = Modifier.testTag("bottom_navigation_bar")
            ) {
                NavigationTab.values().forEach { tab ->
                    val isSelected = currentTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            if (tab == NavigationTab.VIP_DONATION) {
                                viewModel.openVipModal()
                            } else {
                                currentTab = tab
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = EmeraldLive,
                            selectedTextColor = EmeraldLive,
                            unselectedIconColor = SlateTextMuted,
                            unselectedTextColor = SlateTextMuted,
                            indicatorColor = StadiumCardElevated
                        ),
                        modifier = Modifier.testTag("nav_${tab.name.lowercase()}")
                    )
                }
            }
        },
        containerColor = StadiumDark
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                NavigationTab.LIVE_MATCH -> LiveMatchScreen(viewModel = viewModel)
                NavigationTab.IPTV_CHANNELS -> IptvChannelsScreen(viewModel = viewModel)
                NavigationTab.CLUBS_ZONE -> ClubZoneScreen(viewModel = viewModel)
                NavigationTab.VIP_DONATION -> LiveMatchScreen(viewModel = viewModel)
            }
        }
    }

    if (isVipModalOpen) {
        VipDonationDialog(onDismiss = { viewModel.closeVipModal() })
    }
}
