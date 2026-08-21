package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SolarPower
import androidx.compose.material.icons.filled.Streetview
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CompanySettingsEntity
import com.example.ui.theme.GmBlueDark
import com.example.ui.theme.GmBluePrimary
import com.example.ui.theme.GmOrangeSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GmTechTopBar(
    settings: CompanySettingsEntity,
    isAdminUnlocked: Boolean,
    onCallClick: () -> Unit,
    onWhatsAppClick: () -> Unit,
    onAdminBadgeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier.testTag("gmtech_top_bar"),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = GmBluePrimary,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(GmOrangeSecondary),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "GM",
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        fontSize = 17.sp
                    )
                }
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "GM TECH",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Sarl",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = GmOrangeSecondary
                        )
                    }
                    Text(
                        text = "Énergie • Électricité • BTP",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = onCallClick,
                modifier = Modifier.testTag("top_bar_call_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Appeler GM Tech",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = onWhatsAppClick,
                modifier = Modifier.testTag("top_bar_whatsapp_btn")
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF25D366)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "WA",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp
                    )
                }
            }
            IconButton(
                onClick = onAdminBadgeClick,
                modifier = Modifier.testTag("top_bar_admin_btn")
            ) {
                Icon(
                    imageVector = if (isAdminUnlocked) Icons.Default.LockOpen else Icons.Default.Lock,
                    contentDescription = "Espace Admin",
                    tint = if (isAdminUnlocked) GmOrangeSecondary else Color.White.copy(alpha = 0.85f)
                )
            }
        }
    )
}

@Composable
fun StatusBadge(status: String, modifier: Modifier = Modifier) {
    val (bgColor, textColor, label) = when (status.uppercase()) {
        "NOUVEAU" -> Triple(Color(0xFFE0F2FE), Color(0xFF0369A1), "Nouveau")
        "EN_COURS" -> Triple(Color(0xFFFEF3C7), Color(0xFFB45309), "En cours")
        "VALIDÉ", "VALIDE" -> Triple(Color(0xFFDCFCE7), Color(0xFF15803D), "Validé")
        "REJETÉ", "REJETE" -> Triple(Color(0xFFFEE2E2), Color(0xFFB91C1C), "Rejeté")
        "TERMINÉ", "TERMINE" -> Triple(Color(0xFFF3E8FF), Color(0xFF7E22CE), "Terminé")
        else -> Triple(Color(0xFFF1F5F9), Color(0xFF475569), status)
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(12.dp),
        modifier = modifier
    ) {
        Text(
            text = label,
            color = textColor,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun ContactInfoCard(
    settings: CompanySettingsEntity,
    onCallClick: () -> Unit,
    onWhatsAppClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("contact_info_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = GmBlueDark
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(GmOrangeSecondary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Column {
                    Text(
                        text = "Siège & Bureaux GM TECH",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "Kinshasa, RD Congo",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 12.sp
                    )
                }
            }

            Text(
                text = "${settings.address}\n${settings.refLocation}",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                FilledTonalButton(
                    onClick = onCallClick,
                    modifier = Modifier.weight(1f).testTag("contact_card_call_btn"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Appeler", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }

                FilledTonalButton(
                    onClick = onWhatsAppClick,
                    modifier = Modifier.weight(1f).testTag("contact_card_wa_btn"),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "💬 WhatsApp",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

fun getServiceIcon(iconName: String): ImageVector {
    return when (iconName) {
        "solar_power" -> Icons.Default.SolarPower
        "streetview" -> Icons.Default.Streetview
        "water_drop" -> Icons.Default.WaterDrop
        "bolt" -> Icons.Default.Bolt
        "ac_unit" -> Icons.Default.AcUnit
        "plumbing" -> Icons.Default.Plumbing
        "power" -> Icons.Default.Power
        "handyman" -> Icons.Default.Handyman
        "videocam" -> Icons.Default.Videocam
        "shopping_bag" -> Icons.Default.ShoppingBag
        else -> Icons.Default.Build
    }
}
