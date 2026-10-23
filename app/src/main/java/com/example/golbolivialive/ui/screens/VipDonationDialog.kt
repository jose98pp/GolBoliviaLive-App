package com.example.golbolivialive.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.golbolivialive.ui.theme.*

@Composable
fun VipDonationDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.WorkspacePremium, contentDescription = null, tint = GoldStar, modifier = Modifier.size(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Apoyo a la Transmisión & VIP 🇧🇴",
                    color = SlateTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Tu aporte permite mantener los servidores de alta velocidad y la transmisión en vivo de la División Profesional sin cortes.",
                    color = SlateTextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                // QR Simple / Bank Info Card
                Card(
                    colors = CardDefaults.cardColors(containerColor = StadiumDark),
                    shape = RoundedCornerShape(12.dp),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.horizontalGradient(listOf(GoldStar.copy(alpha = 0.5f), EmeraldLive.copy(alpha = 0.5f)))
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            color = StadiumCardElevated,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.size(100.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.QrCode2,
                                    contentDescription = "QR Simple",
                                    tint = GoldStar,
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "QR Simple Bolivia & Tigo Money",
                            color = SlateTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Banco Unión / BNB • Bs. Bolivianos",
                            color = EmeraldGlow,
                            fontSize = 11.sp
                        )
                        Text(
                            text = "Celular de Transferencia: +591 78901234",
                            color = SlateTextMuted,
                            fontSize = 11.sp,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Beneficios Exclusivos VIP:",
                    color = GoldStar,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                val benefits = listOf(
                    "⚡ Transmisión sin cortes ni demoras en 1080p 60fps",
                    "⭐ Insignia Dorada VIP junto a tu nombre en el chat",
                    "🎥 Acceso a cámaras de camerinos y conferencias de prensa",
                    "💬 Mensajes destacados sin límite de velocidad (Slow mode off)"
                )

                benefits.forEach { b ->
                    Row(
                        modifier = Modifier.padding(vertical = 2.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(b, color = SlateTextPrimary, fontSize = 11.sp, lineHeight = 15.sp)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = GoldStar),
                modifier = Modifier.testTag("vip_modal_confirm_button")
            ) {
                Text("Listo", color = StadiumDark, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar", color = SlateTextMuted)
            }
        },
        containerColor = StadiumCardElevated
    )
}
