package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CompanySettingsEntity
import com.example.data.ProductEntity
import com.example.data.QuoteEntity
import com.example.ui.ProductFormState
import com.example.ui.components.StatusBadge
import com.example.ui.theme.GmBlueDark
import com.example.ui.theme.GmBluePrimary
import com.example.ui.theme.GmOrangeSecondary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AdminScreen(
    isAdminUnlocked: Boolean,
    pinInput: String,
    pinError: String?,
    onPinChange: (String) -> Unit,
    onVerifyPin: () -> Unit,
    onLockAdmin: () -> Unit,
    selectedAdminTab: Int,
    onSelectAdminTab: (Int) -> Unit,
    // Data
    quotes: List<QuoteEntity>,
    products: List<ProductEntity>,
    settings: CompanySettingsEntity,
    // Quote Actions
    quoteStatusFilter: String,
    quoteSearchQuery: String,
    onQuoteStatusFilterChange: (String) -> Unit,
    onQuoteSearchChange: (String) -> Unit,
    onUpdateQuoteStatus: (Long, String, String?) -> Unit,
    onDeleteQuote: (QuoteEntity) -> Unit,
    onCallClient: (String) -> Unit,
    onWhatsAppClient: (String, String) -> Unit,
    // Product Actions
    isProductDialogOpen: Boolean,
    productForm: ProductFormState,
    onOpenAddProduct: () -> Unit,
    onOpenEditProduct: (ProductEntity) -> Unit,
    onCloseProductDialog: () -> Unit,
    onProductFormFieldChange: (
        name: String?,
        category: String?,
        price: String?,
        description: String?,
        specifications: String?,
        badge: String?,
        inStock: Boolean?
    ) -> Unit,
    onSaveProduct: () -> Unit,
    onDeleteProduct: (ProductEntity) -> Unit,
    // Settings & CSV Actions
    onSaveSettings: (
        phoneWhatsApp: String,
        phoneCall: String,
        email: String,
        address: String,
        refLocation: String,
        adminPin: String
    ) -> Unit,
    onExportQuotesCsv: () -> Unit,
    onExportProductsCsv: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (!isAdminUnlocked) {
        AdminPinLockView(
            pinInput = pinInput,
            pinError = pinError,
            onPinChange = onPinChange,
            onVerifyPin = onVerifyPin,
            modifier = modifier
        )
    } else {
        AdminDashboardView(
            selectedTab = selectedAdminTab,
            onSelectTab = onSelectAdminTab,
            onLockAdmin = onLockAdmin,
            quotes = quotes,
            products = products,
            settings = settings,
            quoteStatusFilter = quoteStatusFilter,
            quoteSearchQuery = quoteSearchQuery,
            onQuoteStatusFilterChange = onQuoteStatusFilterChange,
            onQuoteSearchChange = onQuoteSearchChange,
            onUpdateQuoteStatus = onUpdateQuoteStatus,
            onDeleteQuote = onDeleteQuote,
            onCallClient = onCallClient,
            onWhatsAppClient = onWhatsAppClient,
            isProductDialogOpen = isProductDialogOpen,
            productForm = productForm,
            onOpenAddProduct = onOpenAddProduct,
            onOpenEditProduct = onOpenEditProduct,
            onCloseProductDialog = onCloseProductDialog,
            onProductFormFieldChange = onProductFormFieldChange,
            onSaveProduct = onSaveProduct,
            onDeleteProduct = onDeleteProduct,
            onSaveSettings = onSaveSettings,
            onExportQuotesCsv = onExportQuotesCsv,
            onExportProductsCsv = onExportProductsCsv,
            modifier = modifier
        )
    }
}

@Composable
fun AdminPinLockView(
    pinInput: String,
    pinError: String?,
    onPinChange: (String) -> Unit,
    onVerifyPin: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .testTag("admin_lock_view"),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(GmBluePrimary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = GmBluePrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Text(
                    text = "Espace Administrateur",
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Veuillez saisir votre code PIN administrateur pour accéder à la gestion des devis, du catalogue et des paramètres.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    lineHeight = 17.sp
                )

                OutlinedTextField(
                    value = pinInput,
                    onValueChange = onPinChange,
                    label = { Text("Code PIN (Par défaut: 1234)") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    singleLine = true,
                    isError = pinError != null,
                    modifier = Modifier.fillMaxWidth().testTag("admin_pin_input")
                )

                pinError?.let { error ->
                    Text(
                        text = error,
                        color = Color(0xFFDC2626),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = onVerifyPin,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("admin_unlock_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GmBluePrimary,
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Default.Key, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Déverrouiller l'Administration", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }
        }
    }
}

@Composable
fun AdminDashboardView(
    selectedTab: Int,
    onSelectTab: (Int) -> Unit,
    onLockAdmin: () -> Unit,
    quotes: List<QuoteEntity>,
    products: List<ProductEntity>,
    settings: CompanySettingsEntity,
    quoteStatusFilter: String,
    quoteSearchQuery: String,
    onQuoteStatusFilterChange: (String) -> Unit,
    onQuoteSearchChange: (String) -> Unit,
    onUpdateQuoteStatus: (Long, String, String?) -> Unit,
    onDeleteQuote: (QuoteEntity) -> Unit,
    onCallClient: (String) -> Unit,
    onWhatsAppClient: (String, String) -> Unit,
    isProductDialogOpen: Boolean,
    productForm: ProductFormState,
    onOpenAddProduct: () -> Unit,
    onOpenEditProduct: (ProductEntity) -> Unit,
    onCloseProductDialog: () -> Unit,
    onProductFormFieldChange: (
        name: String?,
        category: String?,
        price: String?,
        description: String?,
        specifications: String?,
        badge: String?,
        inStock: Boolean?
    ) -> Unit,
    onSaveProduct: () -> Unit,
    onDeleteProduct: (ProductEntity) -> Unit,
    onSaveSettings: (
        phoneWhatsApp: String,
        phoneCall: String,
        email: String,
        address: String,
        refLocation: String,
        adminPin: String
    ) -> Unit,
    onExportQuotesCsv: () -> Unit,
    onExportProductsCsv: () -> Unit,
    modifier: Modifier = Modifier
) {
    val adminTabs = listOf(
        "📊 Tableau de bord",
        "📋 Demandes (${quotes.size})",
        "📦 Produits (${products.size})",
        "📥 Export CSV",
        "⚙️ Paramètres"
    )

    Column(modifier = modifier.fillMaxSize()) {
        // Admin subheader
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(GmBlueDark)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF22C55E))
                )
                Text(
                    text = "Session Administrateur Active",
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            TextButton(
                onClick = onLockAdmin,
                modifier = Modifier.testTag("admin_lock_btn")
            ) {
                Text(text = "Verrouiller", color = GmOrangeSecondary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Horizontal Tab Bar
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = GmBluePrimary,
            edgePadding = 12.dp
        ) {
            adminTabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { onSelectTab(index) },
                    text = {
                        Text(
                            text = title,
                            fontSize = 12.sp,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                )
            }
        }

        // Active Tab Content
        when (selectedTab) {
            0 -> AdminKpiDashboardTab(
                quotes = quotes,
                products = products,
                onNavigateToQuotes = { onSelectTab(1) },
                onNavigateToProducts = { onSelectTab(2) },
                onNavigateToCsv = { onSelectTab(3) }
            )
            1 -> AdminQuotesManagementTab(
                quotes = quotes,
                statusFilter = quoteStatusFilter,
                searchQuery = quoteSearchQuery,
                onStatusFilterChange = onQuoteStatusFilterChange,
                onSearchQueryChange = onQuoteSearchChange,
                onUpdateStatus = onUpdateQuoteStatus,
                onDeleteQuote = onDeleteQuote,
                onCallClient = onCallClient,
                onWhatsAppClient = onWhatsAppClient
            )
            2 -> AdminProductsManagementTab(
                products = products,
                onOpenAddProduct = onOpenAddProduct,
                onOpenEditProduct = onOpenEditProduct,
                onDeleteProduct = onDeleteProduct
            )
            3 -> AdminCsvExportTab(
                quotes = quotes,
                products = products,
                onExportQuotes = onExportQuotesCsv,
                onExportProducts = onExportProductsCsv
            )
            4 -> AdminSettingsTab(
                settings = settings,
                onSaveSettings = onSaveSettings
            )
        }
    }

    // Product Add/Edit Dialog
    if (isProductDialogOpen) {
        ProductEditDialog(
            form = productForm,
            onClose = onCloseProductDialog,
            onFieldChange = onProductFormFieldChange,
            onSave = onSaveProduct
        )
    }
}

@Composable
fun AdminKpiDashboardTab(
    quotes: List<QuoteEntity>,
    products: List<ProductEntity>,
    onNavigateToQuotes: () -> Unit,
    onNavigateToProducts: () -> Unit,
    onNavigateToCsv: () -> Unit,
    modifier: Modifier = Modifier
) {
    val newCount = quotes.count { it.status.equals("NOUVEAU", ignoreCase = true) }
    val inProgressCount = quotes.count { it.status.equals("EN_COURS", ignoreCase = true) }
    val validatedCount = quotes.count { it.status.equals("VALIDÉ", ignoreCase = true) || it.status.equals("VALIDE", ignoreCase = true) }
    val inStockCount = products.count { it.inStock }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("admin_kpi_tab"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Vue d'ensemble de l'activité",
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // KPI Cards Row 1
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AdminStatMetricCard(
                    title = "Total Devis",
                    count = quotes.size.toString(),
                    subtitle = "$newCount nouveau(x)",
                    bgColor = Color(0xFFE0F2FE),
                    textColor = Color(0xFF0369A1),
                    modifier = Modifier.weight(1f).clickable { onNavigateToQuotes() }
                )
                AdminStatMetricCard(
                    title = "Devis Validés",
                    count = validatedCount.toString(),
                    subtitle = "$inProgressCount en cours",
                    bgColor = Color(0xFFDCFCE7),
                    textColor = Color(0xFF15803D),
                    modifier = Modifier.weight(1f).clickable { onNavigateToQuotes() }
                )
            }
        }

        // KPI Cards Row 2
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AdminStatMetricCard(
                    title = "Catalogue",
                    count = products.size.toString(),
                    subtitle = "$inStockCount en stock",
                    bgColor = Color(0xFFFFEDD5),
                    textColor = Color(0xFFC2410C),
                    modifier = Modifier.weight(1f).clickable { onNavigateToProducts() }
                )
                AdminStatMetricCard(
                    title = "Exportations",
                    count = "CSV",
                    subtitle = "Clients & Produits",
                    bgColor = Color(0xFFF3E8FF),
                    textColor = Color(0xFF7E22CE),
                    modifier = Modifier.weight(1f).clickable { onNavigateToCsv() }
                )
            }
        }

        // Recent quotes feed
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Dernières demandes reçues",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Gérer >",
                            color = GmBluePrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            modifier = Modifier.clickable { onNavigateToQuotes() }
                        )
                    }

                    if (quotes.isEmpty()) {
                        Text(
                            text = "Aucune demande de devis enregistrée.",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    } else {
                        quotes.take(4).forEach { quote ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onNavigateToQuotes() }
                                    .padding(vertical = 4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "${quote.quoteNumber} • ${quote.clientName}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    StatusBadge(status = quote.status)
                                }
                                Text(
                                    text = quote.serviceRequested,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                HorizontalDivider(
                                    modifier = Modifier.padding(top = 6.dp),
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.15f)
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
fun AdminStatMetricCard(
    title: String,
    count: String,
    subtitle: String,
    bgColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = bgColor
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = textColor)
            Text(text = count, fontSize = 24.sp, fontWeight = FontWeight.Black, color = textColor)
            Text(text = subtitle, fontSize = 11.sp, color = textColor.copy(alpha = 0.8f))
        }
    }
}

@Composable
fun AdminQuotesManagementTab(
    quotes: List<QuoteEntity>,
    statusFilter: String,
    searchQuery: String,
    onStatusFilterChange: (String) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onUpdateStatus: (Long, String, String?) -> Unit,
    onDeleteQuote: (QuoteEntity) -> Unit,
    onCallClient: (String) -> Unit,
    onWhatsAppClient: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val statuses = listOf("Tous", "NOUVEAU", "EN_COURS", "VALIDÉ", "REJETÉ", "TERMINÉ")

    val filtered = remember(quotes, statusFilter, searchQuery) {
        quotes.filter { q ->
            val matchesStatus = (statusFilter == "Tous" || q.status.equals(statusFilter, ignoreCase = true))
            val matchesSearch = searchQuery.isBlank() ||
                    q.quoteNumber.contains(searchQuery, ignoreCase = true) ||
                    q.clientName.contains(searchQuery, ignoreCase = true) ||
                    q.clientPhone.contains(searchQuery, ignoreCase = true) ||
                    q.serviceRequested.contains(searchQuery, ignoreCase = true)
            matchesStatus && matchesSearch
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("admin_quotes_tab"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                placeholder = { Text("Rechercher par n° devis, nom, téléphone...", fontSize = 13.sp) },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().testTag("admin_quote_search_input")
            )
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(statuses) { st ->
                    val isSelected = (statusFilter == st)
                    FilterChip(
                        selected = isSelected,
                        onClick = { onStatusFilterChange(st) },
                        label = { Text(st, fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = GmBluePrimary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        if (filtered.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Aucune demande ne correspond à ces critères.",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
        } else {
            items(filtered, key = { it.id }) { quote ->
                AdminQuoteItemCard(
                    quote = quote,
                    onUpdateStatus = { newSt, notes -> onUpdateStatus(quote.id, newSt, notes) },
                    onDelete = { onDeleteQuote(quote) },
                    onCallClient = { onCallClient(quote.clientPhone) },
                    onWhatsAppClient = {
                        val reply = "Bonjour ${quote.clientName}, GM Tech fait suite à votre demande de devis N° ${quote.quoteNumber} pour ${quote.serviceRequested}."
                        onWhatsAppClient(quote.clientPhone, reply)
                    }
                )
            }
        }
    }
}

@Composable
fun AdminQuoteItemCard(
    quote: QuoteEntity,
    onUpdateStatus: (String, String?) -> Unit,
    onDelete: () -> Unit,
    onCallClient: () -> Unit,
    onWhatsAppClient: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }
    var isExpanded by remember { mutableStateOf(false) }
    var notesText by remember { mutableStateOf(quote.internalNotes) }

    val dateStr = remember(quote.createdAt) {
        SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH).format(Date(quote.createdAt))
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = quote.quoteNumber,
                        fontWeight = FontWeight.Black,
                        fontSize = 14.sp,
                        color = GmOrangeSecondary
                    )
                    StatusBadge(status = quote.status)
                }

                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Actions")
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text("Marquer comme 'NOUVEAU'") },
                            onClick = { onUpdateStatus("NOUVEAU", null); showMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Marquer comme 'EN COURS'") },
                            onClick = { onUpdateStatus("EN_COURS", null); showMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Marquer comme 'VALIDÉ'") },
                            onClick = { onUpdateStatus("VALIDÉ", null); showMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Marquer comme 'TERMINÉ'") },
                            onClick = { onUpdateStatus("TERMINÉ", null); showMenu = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Marquer comme 'REJETÉ'") },
                            onClick = { onUpdateStatus("REJETÉ", null); showMenu = false }
                        )
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = { Text("Supprimer la demande", color = Color.Red) },
                            onClick = { onDelete(); showMenu = false }
                        )
                    }
                }
            }

            Text(
                text = "${quote.clientName} • ${quote.clientPhone}",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Service : ${quote.serviceRequested}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = GmBluePrimary
            )

            Text(
                text = quote.projectDetails,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                maxLines = if (isExpanded) 10 else 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Budget : ${quote.estimatedBudget}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GmOrangeSecondary
                )
                Text(
                    text = dateStr,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }

            // Quick Client Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onCallClient,
                    modifier = Modifier.weight(1f).height(36.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GmBluePrimary,
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Appeler", fontSize = 11.sp)
                }

                Button(
                    onClick = onWhatsAppClient,
                    modifier = Modifier.weight(1.2f).height(36.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF25D366),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "💬 WhatsApp", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier.weight(0.9f).height(36.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = if (isExpanded) "Moins" else "Notes", fontSize = 11.sp)
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    OutlinedTextField(
                        value = notesText,
                        onValueChange = { notesText = it },
                        label = { Text("Notes internes (Suivi / Visite technique)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = { onUpdateStatus(quote.status, notesText) },
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(containerColor = GmBluePrimary)
                    ) {
                        Text(text = "Enregistrer la note", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun AdminProductsManagementTab(
    products: List<ProductEntity>,
    onOpenAddProduct: () -> Unit,
    onOpenEditProduct: (ProductEntity) -> Unit,
    onDeleteProduct: (ProductEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("admin_products_tab"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Button(
                onClick = onOpenAddProduct,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp)
                    .testTag("admin_add_product_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GmOrangeSecondary,
                    contentColor = Color.White
                )
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Ajouter un nouveau produit", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
        }

        items(products, key = { it.id }) { product ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Surface(
                                color = GmBluePrimary.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = product.category,
                                    color = GmBluePrimary,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                            Text(
                                text = if (product.inStock) "En Stock" else "Sur commande",
                                fontSize = 10.sp,
                                color = if (product.inStock) Color(0xFF16A34A) else Color(0xFFD97706),
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = product.name,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 2
                        )

                        Text(
                            text = "${product.price} ${product.currency}",
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            color = GmOrangeSecondary
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        IconButton(onClick = { onOpenEditProduct(product) }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Modifier", tint = GmBluePrimary)
                        }
                        IconButton(onClick = { onDeleteProduct(product) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Supprimer", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductEditDialog(
    form: ProductFormState,
    onClose: () -> Unit,
    onFieldChange: (
        name: String?,
        category: String?,
        price: String?,
        description: String?,
        specifications: String?,
        badge: String?,
        inStock: Boolean?
    ) -> Unit,
    onSave: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(
                text = if (form.id == 0L) "Ajouter un produit" else "Modifier le produit",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = form.name,
                    onValueChange = { onFieldChange(it, null, null, null, null, null, null) },
                    label = { Text("Nom de l'article *") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = form.category,
                        onValueChange = { onFieldChange(null, it, null, null, null, null, null) },
                        label = { Text("Catégorie") },
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = form.price,
                        onValueChange = { onFieldChange(null, null, it, null, null, null, null) },
                        label = { Text("Prix (USD)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }
                OutlinedTextField(
                    value = form.badge,
                    onValueChange = { onFieldChange(null, null, null, null, null, it, null) },
                    label = { Text("Badge (ex: Populaire, Promo)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = form.specifications,
                    onValueChange = { onFieldChange(null, null, null, null, it, null, null) },
                    label = { Text("Spécifications (séparées par |)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = form.description,
                    onValueChange = { onFieldChange(null, null, null, it, null, null, null) },
                    label = { Text("Description") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Disponible en stock :", fontSize = 13.sp)
                    Switch(
                        checked = form.inStock,
                        onCheckedChange = { onFieldChange(null, null, null, null, null, null, it) },
                        colors = SwitchDefaults.colors(checkedThumbColor = GmOrangeSecondary)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onSave,
                colors = ButtonDefaults.buttonColors(containerColor = GmBluePrimary)
            ) {
                Text("Enregistrer")
            }
        },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text("Annuler")
            }
        }
    )
}

@Composable
fun AdminCsvExportTab(
    quotes: List<QuoteEntity>,
    products: List<ProductEntity>,
    onExportQuotes: () -> Unit,
    onExportProducts: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("admin_csv_tab"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "EXPORTATION DE DONNÉES CSV",
                    color = GmOrangeSecondary,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 12.sp
                )
                Text(
                    text = "Télécharger & Partager vos listes",
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Exportez en format standard CSV compatible Excel, Google Sheets, ou partagez directement par WhatsApp ou email.",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }
        }

        // Quotes CSV Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(GmBluePrimary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.RequestQuote, contentDescription = null, tint = GmBluePrimary)
                        }
                        Column {
                            Text(text = "Demandes de Devis (Clients)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(text = "${quotes.size} ligne(s) enregistrée(s)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                    }

                    Text(
                        text = "Contient : Numéro de devis, date, nom client, numéro téléphone, email, ville, service, description des travaux, budget estimé, statut et notes internes.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        lineHeight = 16.sp
                    )

                    Button(
                        onClick = onExportQuotes,
                        modifier = Modifier.fillMaxWidth().height(46.dp).testTag("export_quotes_csv_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GmBluePrimary)
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Exporter les Demandes en CSV", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        // Products CSV Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(GmOrangeSecondary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Inventory, contentDescription = null, tint = GmOrangeSecondary)
                        }
                        Column {
                            Text(text = "Catalogue des Produits", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(text = "${products.size} article(s) enregistrés", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        }
                    }

                    Text(
                        text = "Contient : ID produit, nom, catégorie, prix USD, disponibilité stock, badge, spécifications techniques et description.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        lineHeight = 16.sp
                    )

                    Button(
                        onClick = onExportProducts,
                        modifier = Modifier.fillMaxWidth().height(46.dp).testTag("export_products_csv_btn"),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GmOrangeSecondary)
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Exporter le Catalogue en CSV", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun AdminSettingsTab(
    settings: CompanySettingsEntity,
    onSaveSettings: (
        phoneWhatsApp: String,
        phoneCall: String,
        email: String,
        address: String,
        refLocation: String,
        adminPin: String
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var phoneWhatsApp by remember { mutableStateOf(settings.phoneWhatsApp) }
    var phoneCall by remember { mutableStateOf(settings.phoneCall) }
    var email by remember { mutableStateOf(settings.email) }
    var address by remember { mutableStateOf(settings.address) }
    var refLocation by remember { mutableStateOf(settings.refLocation) }
    var adminPin by remember { mutableStateOf(settings.adminPin) }
    var isSavedNoticeVisible by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("admin_settings_tab"),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                text = "Paramètres de l'Entreprise & Contact",
                fontWeight = FontWeight.Black,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if (isSavedNoticeVisible) {
            item {
                Surface(
                    color = Color(0xFFDCFCE7),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color(0xFF15803D))
                        Text(
                            text = "Paramètres mis à jour avec succès !",
                            color = Color(0xFF15803D),
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Numéros de Contact WhatsApp & Téléphone",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = GmBluePrimary
                    )

                    OutlinedTextField(
                        value = phoneWhatsApp,
                        onValueChange = { phoneWhatsApp = it },
                        label = { Text("Numéro WhatsApp officiel (ex: +243994116034)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("settings_input_wa")
                    )

                    OutlinedTextField(
                        value = phoneCall,
                        onValueChange = { phoneCall = it },
                        label = { Text("Numéro d'appel téléphonique (ex: +243 994 116 034)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("settings_input_phone")
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email professionnel") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("settings_input_email")
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Adresse Physique & Références",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = GmBluePrimary
                    )

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Adresse") },
                        modifier = Modifier.fillMaxWidth().testTag("settings_input_address")
                    )

                    OutlinedTextField(
                        value = refLocation,
                        onValueChange = { refLocation = it },
                        label = { Text("Point de référence / Niveau") },
                        modifier = Modifier.fillMaxWidth().testTag("settings_input_ref")
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Sécurité Administrateur",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = GmBluePrimary
                    )

                    OutlinedTextField(
                        value = adminPin,
                        onValueChange = { adminPin = it },
                        label = { Text("Code PIN d'accès Admin (4 chiffres)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("settings_input_pin")
                    )

                    Button(
                        onClick = {
                            onSaveSettings(
                                phoneWhatsApp,
                                phoneCall,
                                email,
                                address,
                                refLocation,
                                adminPin
                            )
                            isSavedNoticeVisible = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("settings_save_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GmOrangeSecondary,
                            contentColor = Color.White
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Enregistrer les modifications", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
