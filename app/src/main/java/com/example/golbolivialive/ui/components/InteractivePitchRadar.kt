package com.example.golbolivialive.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.golbolivialive.ui.theme.EmeraldLive

@Composable
fun InteractivePitchRadar(
    homeClubShortName: String = "Bolívar",
    awayClubShortName: String = "The Strongest",
    homeColor: Color = Color(0xFF38BDF8),
    awayColor: Color = Color(0xFFEAB308),
    onRetryStream: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "pitch_animation")
    
    // Animate ball position horizontally across pitch
    val ballXFraction by infiniteTransition.animateFloat(
        initialValue = 0.35f,
        targetValue = 0.75f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ball_x"
    )

    val ballYFraction by infiniteTransition.animateFloat(
        initialValue = 0.40f,
        targetValue = 0.65f,
        animationSpec = infiniteRepeatable(
            animation = tween(2800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "ball_y"
    )

    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 6f,
        targetValue = 18f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseOutQuad),
            repeatMode = RepeatMode.Restart
        ),
        label = "pulse"
    )

    val simulatedActions = listOf(
        "Ataque peligroso de Bolívar por banda izquierda",
        "Presión alta de The Strongest en medio campo",
        "¡Remate potente desviado al córner!",
        "Pase filtrado al área chica",
        "Disputa de balón en zona de tres cuartos"
    )

    var currentActionIndex by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            kotlinx.coroutines.delay(4500)
            currentActionIndex = (currentActionIndex + 1) % simulatedActions.size
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0F291E))
            .testTag("interactive_pitch_radar")
    ) {
        // Pitch lines drawn on Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val fieldW = size.width
            val fieldH = size.height

            // Grass field outline
            drawRect(
                color = Color(0xFF1B4332),
                size = size
            )

            val lineStroke = Stroke(width = 2.dp.toPx())
            val lineStrokeDashed = Stroke(
                width = 2.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )

            // Outer boundary margin
            val m = 12.dp.toPx()
            drawRect(
                color = Color(0x66FFFFFF),
                topLeft = Offset(m, m),
                size = Size(fieldW - 2 * m, fieldH - 2 * m),
                style = lineStroke
            )

            // Halfway line
            val midX = fieldW / 2
            drawLine(
                color = Color(0x66FFFFFF),
                start = Offset(midX, m),
                end = Offset(midX, fieldH - m),
                strokeWidth = 2.dp.toPx()
            )

            // Center circle
            val centerRadius = (fieldH / 4).coerceAtMost(50.dp.toPx())
            drawCircle(
                color = Color(0x66FFFFFF),
                radius = centerRadius,
                center = Offset(midX, fieldH / 2),
                style = lineStroke
            )

            // Left Penalty Box (Home)
            val boxW = fieldW * 0.16f
            val boxH = fieldH * 0.50f
            drawRect(
                color = Color(0x44FFFFFF),
                topLeft = Offset(m, (fieldH - boxH) / 2),
                size = Size(boxW, boxH),
                style = lineStroke
            )

            // Right Penalty Box (Away)
            drawRect(
                color = Color(0x44FFFFFF),
                topLeft = Offset(fieldW - m - boxW, (fieldH - boxH) / 2),
                size = Size(boxW, boxH),
                style = lineStroke
            )

            // Ball position pulse
            val bx = m + (fieldW - 2 * m) * ballXFraction
            val by = m + (fieldH - 2 * m) * ballYFraction

            drawCircle(
                color = EmeraldLive.copy(alpha = 0.45f),
                radius = pulseRadius * 2f,
                center = Offset(bx, by)
            )

            // The ball
            drawCircle(
                color = Color.White,
                radius = 6.dp.toPx(),
                center = Offset(bx, by)
            )
            drawCircle(
                color = Color.Black,
                radius = 3.dp.toPx(),
                center = Offset(bx, by)
            )
        }

        // Top Header inside Pitch: Live Match Radar & Club tags
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color.Black.copy(alpha = 0.75f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(homeColor)
                    )
                    Text(
                        text = homeClubShortName,
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "vs", color = Color.Gray, fontSize = 10.sp)
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(awayColor)
                    )
                    Text(
                        text = awayClubShortName,
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Retry button for video stream
            Button(
                onClick = onRetryStream,
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldLive.copy(alpha = 0.9f)),
                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.height(28.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reintentar Streaming",
                    tint = Color.Black,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Reconectar HD",
                    color = Color.Black,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Bottom Banner inside Pitch: Tactical incident text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.Black.copy(alpha = 0.70f))
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "📡 RADAR 2D: ${simulatedActions[currentActionIndex]}",
                    color = EmeraldLive,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
                Text(
                    text = "Min 78'",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
