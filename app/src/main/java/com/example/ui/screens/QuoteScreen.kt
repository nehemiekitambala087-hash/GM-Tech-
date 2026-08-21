package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.InitialData
import com.example.data.QuoteEntity
import com.example.ui.QuoteFormState
import com.example.ui.theme.GmBlueDark
import com.example.ui.theme.GmBluePrimary
import com.example.ui.theme.GmOrangeSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(
    formState: QuoteFormState,
    onFieldChange: (
        name: String?,
        phone: String?,
        email: String?,
        city: String?,
        address: String?,
        service: String?,
        details: String?,
        budget: String?
    ) -> Unit,
    onSubmit: ((QuoteEntity) -> Unit) -> Unit,
    onReset: () -> Unit,
    onOpenWhatsApp: (QuoteEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val serviceOptions = remember {
        InitialData.SERVICES.map { it.title } + listOf(
            "Vente de matériels & outillage",
            "Autre projet sur mesure"
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("quote_screen_lazy_column"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (formState.submittedQuote != null) {
            // Receipt Screen with Instant WhatsApp Forward
            item {
                QuoteSuccessReceiptCard(
                    quote = formState.submittedQuote,
                    onOpenWhatsApp = { onOpenWhatsApp(formState.submittedQuote) },
                    onReset = onReset
                )
            }
        } else {
            // Header
            item {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "DEVIS GRATUIT EN LIGNE",
                        color = GmOrangeSecondary,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Demande de Devis Instantanée",
                        fontWeight = FontWeight.Black,
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "Remplissez le formulaire ci-dessous. Un numéro de dossier unique sera généré automatiquement et vous pourrez transmettre votre demande en 1 clic sur WhatsApp.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        lineHeight = 18.sp
                    )
                }
            }

            // Error notice if any
            formState.errorMessage?.let { error ->
                item {
                    Surface(
                        color = Color(0xFFFEE2E2),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = Color(0xFFDC2626),
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = error,
                                color = Color(0xFF991B1B),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Form Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "1. Vos coordonnées",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = GmBluePrimary
                        )

                        OutlinedTextField(
                            value = formState.clientName,
                            onValueChange = { onFieldChange(it, null, null, null, null, null, null, null) },
                            label = { Text("Nom complet ou Société *") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = GmBluePrimary)
                            },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("quote_input_name")
                        )

                        OutlinedTextField(
                            value = formState.clientPhone,
                            onValueChange = { onFieldChange(null, it, null, null, null, null, null, null) },
                            label = { Text("Numéro Téléphone / WhatsApp *") },
                            placeholder = { Text("+243 81 ...") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = GmBluePrimary)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("quote_input_phone")
                        )

                        OutlinedTextField(
                            value = formState.clientEmail,
                            onValueChange = { onFieldChange(null, null, it, null, null, null, null, null) },
                            label = { Text("Adresse Email (Optionnel)") },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = GmBluePrimary)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("quote_input_email")
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedTextField(
                                value = formState.clientCity,
                                onValueChange = { onFieldChange(null, null, null, it, null, null, null, null) },
                                label = { Text("Ville") },
                                singleLine = true,
                                modifier = Modifier.weight(1f).testTag("quote_input_city")
                            )

                            OutlinedTextField(
                                value = formState.clientAddress,
                                onValueChange = { onFieldChange(null, null, null, null, it, null, null, null) },
                                label = { Text("Commune / Quartier") },
                                singleLine = true,
                                modifier = Modifier.weight(1.3f).testTag("quote_input_address")
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "2. Votre projet & Service souhaité",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = GmBluePrimary
                        )

                        // Service Selector Dropdown
                        ExposedDropdownMenuBox(
                            expanded = isDropdownExpanded,
                            onExpandedChange = { isDropdownExpanded = !isDropdownExpanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = formState.serviceRequested,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Service demandé *") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                                    .testTag("quote_service_dropdown")
                            )

                            ExposedDropdownMenu(
                                expanded = isDropdownExpanded,
                                onDismissRequest = { isDropdownExpanded = false }
                            ) {
                                serviceOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option, fontSize = 13.sp) },
                                        onClick = {
                                            onFieldChange(null, null, null, null, null, option, null, null)
                                            isDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }

                        OutlinedTextField(
                            value = formState.projectDetails,
                            onValueChange = { onFieldChange(null, null, null, null, null, null, it, null) },
                            label = { Text("Description des travaux / Besoins *") },
                            placeholder = { Text("Ex: Installation solaire 5kVA pour maison 4 chambres avec clim...") },
                            minLines = 3,
                            maxLines = 6,
                            modifier = Modifier.fillMaxWidth().testTag("quote_input_details")
                        )

                        OutlinedTextField(
                            value = formState.estimatedBudget,
                            onValueChange = { onFieldChange(null, null, null, null, null, null, null, it) },
                            label = { Text("Budget estimé (Optionnel)") },
                            placeholder = { Text("Ex: 3 000 USD ou À étudier") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth().testTag("quote_input_budget")
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Button(
                            onClick = {
                                onSubmit { /* Handled in VM */ }
                            },
                            enabled = !formState.isSubmitting,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("quote_submit_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GmOrangeSecondary,
                                contentColor = Color.White
                            )
                        ) {
                            if (formState.isSubmitting) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp
                                )
                            } else {
                                Icon(imageVector = Icons.Default.Send, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Générer mon devis & Réf",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuoteSuccessReceiptCard(
    quote: QuoteEntity,
    onOpenWhatsApp: () -> Unit,
    onReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("quote_success_card"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFDCFCE7)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF16A34A),
                    modifier = Modifier.size(36.dp)
                )
            }

            Text(
                text = "Demande enregistrée avec succès !",
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Surface(
                color = GmBluePrimary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "VOTRE RÉFÉRENCE DE DOSSIER",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = GmBluePrimary,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = quote.quoteNumber,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        color = GmOrangeSecondary
                    )
                }
            }

            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    ReceiptRow(label = "Client", value = quote.clientName)
                    ReceiptRow(label = "Téléphone", value = quote.clientPhone)
                    ReceiptRow(label = "Ville", value = "${quote.clientCity} ${quote.clientAddress}")
                    ReceiptRow(label = "Service", value = quote.serviceRequested)
                    ReceiptRow(label = "Budget estimé", value = quote.estimatedBudget)
                }
            }

            Text(
                text = "Cliquez sur le bouton ci-dessous pour transmettre automatiquement cette demande sur le WhatsApp officiel de GM Tech.",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                lineHeight = 16.sp
            )

            Button(
                onClick = onOpenWhatsApp,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("quote_success_whatsapp_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF25D366),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "💬 Transmettre sur WhatsApp",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            OutlinedButton(
                onClick = onReset,
                modifier = Modifier.fillMaxWidth().height(44.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Créer une nouvelle demande", fontSize = 13.sp)
            }
        }
    }
}

@Composable
fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label :",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
