package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.GmTechViewModel
import com.example.ui.MainTab
import com.example.ui.components.GmTechTopBar
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.CatalogScreen
import com.example.ui.screens.GalleryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.QuoteScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.theme.GmBlueDark
import com.example.ui.theme.GmBluePrimary
import com.example.ui.theme.GmOrangeSecondary
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                GmTechApp()
            }
        }
    }
}

@Composable
fun GmTechApp(viewModel: GmTechViewModel = viewModel()) {
    val context = LocalContext.current

    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val settings by viewModel.companySettings.collectAsStateWithLifecycle()
    val products by viewModel.filteredProducts.collectAsStateWithLifecycle()
    val allProducts by viewModel.allProducts.collectAsStateWithLifecycle()
    val allQuotes by viewModel.allQuotes.collectAsStateWithLifecycle()
    val quoteFormState by viewModel.quoteFormState.collectAsStateWithLifecycle()
    val catalogSearchQuery by viewModel.catalogSearchQuery.collectAsStateWithLifecycle()
    val catalogCategoryFilter by viewModel.catalogCategoryFilter.collectAsStateWithLifecycle()

    // Admin State
    val isAdminUnlocked by viewModel.isAdminUnlocked.collectAsStateWithLifecycle()
    val adminPinInput by viewModel.adminPinInput.collectAsStateWithLifecycle()
    val pinError by viewModel.pinError.collectAsStateWithLifecycle()
    val selectedAdminTab by viewModel.adminSelectedTab.collectAsStateWithLifecycle()
    val adminQuoteStatusFilter by viewModel.adminQuoteStatusFilter.collectAsStateWithLifecycle()
    val adminQuoteSearchQuery by viewModel.adminQuoteSearchQuery.collectAsStateWithLifecycle()
    val isProductDialogOpen by viewModel.isProductDialogOpen.collectAsStateWithLifecycle()
    val productForm by viewModel.productForm.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            GmTechTopBar(
                settings = settings,
                isAdminUnlocked = isAdminUnlocked,
                onCallClick = { viewModel.makeDirectCall(context) },
                onWhatsAppClick = { viewModel.openWhatsAppDirect(context) },
                onAdminBadgeClick = { viewModel.selectTab(MainTab.ADMIN) }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = GmBluePrimary,
                tonalElevation = 8.dp,
                modifier = Modifier.testTag("gmtech_bottom_bar")
            ) {
                NavigationBarItem(
                    selected = currentTab == MainTab.ACCUEIL,
                    onClick = { viewModel.selectTab(MainTab.ACCUEIL) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
                    label = { Text("Accueil", fontSize = 10.sp, fontWeight = if (currentTab == MainTab.ACCUEIL) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = GmBluePrimary,
                        indicatorColor = GmBluePrimary
                    )
                )

                NavigationBarItem(
                    selected = currentTab == MainTab.SERVICES,
                    onClick = { viewModel.selectTab(MainTab.SERVICES) },
                    icon = { Icon(Icons.Default.Construction, contentDescription = "Services") },
                    label = { Text("Services", fontSize = 10.sp, fontWeight = if (currentTab == MainTab.SERVICES) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = GmBluePrimary,
                        indicatorColor = GmBluePrimary
                    )
                )

                NavigationBarItem(
                    selected = currentTab == MainTab.CATALOGUE,
                    onClick = { viewModel.selectTab(MainTab.CATALOGUE) },
                    icon = { Icon(Icons.Default.ShoppingBag, contentDescription = "Catalogue") },
                    label = { Text("Catalogue", fontSize = 10.sp, fontWeight = if (currentTab == MainTab.CATALOGUE) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = GmBluePrimary,
                        indicatorColor = GmBluePrimary
                    )
                )

                NavigationBarItem(
                    selected = currentTab == MainTab.REALISATIONS,
                    onClick = { viewModel.selectTab(MainTab.REALISATIONS) },
                    icon = { Icon(Icons.Default.PhotoLibrary, contentDescription = "Réalisations") },
                    label = { Text("Projets", fontSize = 10.sp, fontWeight = if (currentTab == MainTab.REALISATIONS) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = GmBluePrimary,
                        indicatorColor = GmBluePrimary
                    )
                )

                NavigationBarItem(
                    selected = currentTab == MainTab.DEVIS,
                    onClick = { viewModel.selectTab(MainTab.DEVIS) },
                    icon = { Icon(Icons.Default.RequestQuote, contentDescription = "Devis") },
                    label = { Text("Devis", fontSize = 10.sp, fontWeight = if (currentTab == MainTab.DEVIS) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = GmOrangeSecondary,
                        indicatorColor = GmOrangeSecondary
                    )
                )

                NavigationBarItem(
                    selected = currentTab == MainTab.ADMIN,
                    onClick = { viewModel.selectTab(MainTab.ADMIN) },
                    icon = { Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin") },
                    label = { Text("Admin", fontSize = 10.sp, fontWeight = if (currentTab == MainTab.ADMIN) FontWeight.Bold else FontWeight.Normal) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.White,
                        selectedTextColor = GmBlueDark,
                        indicatorColor = GmBlueDark
                    )
                )
            }
        },
        floatingActionButton = {
            if (currentTab != MainTab.ADMIN) {
                FloatingActionButton(
                    onClick = { viewModel.openWhatsAppDirect(context) },
                    containerColor = Color(0xFF25D366),
                    contentColor = Color.White,
                    modifier = Modifier.testTag("global_whatsapp_fab")
                ) {
                    Icon(imageVector = Icons.Default.Chat, contentDescription = "WhatsApp GM Tech")
                }
            }
        }
    ) { innerPadding ->
        Crossfade(
            targetState = currentTab,
            label = "ScreenTransition",
            modifier = Modifier.padding(innerPadding)
        ) { tab ->
            when (tab) {
                MainTab.ACCUEIL -> HomeScreen(
                    services = viewModel.servicesList,
                    products = allProducts,
                    settings = settings,
                    onNavigateToServices = { viewModel.selectTab(MainTab.SERVICES) },
                    onNavigateToCatalog = { viewModel.selectTab(MainTab.CATALOGUE) },
                    onNavigateToQuote = { viewModel.selectTab(MainTab.DEVIS) },
                    onSelectService = { service -> viewModel.initiateQuoteForService(service.title) },
                    onSelectProduct = { product -> viewModel.initiateQuoteForProduct(product) },
                    onDirectCall = { viewModel.makeDirectCall(context) },
                    onDirectWhatsApp = { viewModel.openWhatsAppDirect(context) }
                )

                MainTab.SERVICES -> ServicesScreen(
                    services = viewModel.servicesList,
                    onQuoteForService = { serviceTitle ->
                        viewModel.initiateQuoteForService(serviceTitle)
                    },
                    onWhatsAppForService = { msg ->
                        viewModel.openWhatsAppDirect(context, msg)
                    }
                )

                MainTab.CATALOGUE -> CatalogScreen(
                    products = products,
                    searchQuery = catalogSearchQuery,
                    selectedCategory = catalogCategoryFilter,
                    onSearchChange = { viewModel.setCatalogSearch(it) },
                    onCategoryChange = { viewModel.setCatalogCategory(it) },
                    onInitiateQuote = { product ->
                        viewModel.initiateQuoteForProduct(product)
                    },
                    onWhatsAppBuy = { product ->
                        viewModel.openWhatsAppForProduct(context, product)
                    }
                )

                MainTab.REALISATIONS -> GalleryScreen(
                    projects = viewModel.galleryProjects,
                    onRequestSimilarQuote = { project ->
                        viewModel.updateQuoteFormField(
                            service = project.title,
                            details = "Je souhaite un projet similaire à : ${project.title} (${project.location}, ${project.keyMetric})"
                        )
                        viewModel.selectTab(MainTab.DEVIS)
                    }
                )

                MainTab.DEVIS -> QuoteScreen(
                    formState = quoteFormState,
                    onFieldChange = { name, phone, email, city, address, service, details, budget ->
                        viewModel.updateQuoteFormField(name, phone, email, city, address, service, details, budget)
                    },
                    onSubmit = { onSuccess ->
                        viewModel.submitQuote(onSuccess)
                    },
                    onReset = { viewModel.resetQuoteForm() },
                    onOpenWhatsApp = { quote ->
                        viewModel.openWhatsAppForQuote(context, quote)
                    }
                )

                MainTab.ADMIN -> AdminScreen(
                    isAdminUnlocked = isAdminUnlocked,
                    pinInput = adminPinInput,
                    pinError = pinError,
                    onPinChange = { viewModel.onPinInputChange(it) },
                    onVerifyPin = { viewModel.verifyAdminPin() },
                    onLockAdmin = { viewModel.lockAdmin() },
                    selectedAdminTab = selectedAdminTab,
                    onSelectAdminTab = { viewModel.setAdminTab(it) },
                    quotes = allQuotes,
                    products = allProducts,
                    settings = settings,
                    quoteStatusFilter = adminQuoteStatusFilter,
                    quoteSearchQuery = adminQuoteSearchQuery,
                    onQuoteStatusFilterChange = { viewModel.setAdminQuoteStatusFilter(it) },
                    onQuoteSearchChange = { viewModel.setAdminQuoteSearch(it) },
                    onUpdateQuoteStatus = { id, status, notes ->
                        viewModel.updateQuoteStatus(id, status, notes)
                    },
                    onDeleteQuote = { quote -> viewModel.deleteQuote(quote) },
                    onCallClient = { phone -> viewModel.callClient(context, phone) },
                    onWhatsAppClient = { phone, msg -> viewModel.whatsAppClient(context, phone, msg) },
                    isProductDialogOpen = isProductDialogOpen,
                    productForm = productForm,
                    onOpenAddProduct = { viewModel.openAddProductDialog() },
                    onOpenEditProduct = { product -> viewModel.openEditProductDialog(product) },
                    onCloseProductDialog = { viewModel.closeProductDialog() },
                    onProductFormFieldChange = { name, cat, price, desc, specs, badge, stock ->
                        viewModel.updateProductFormField(name, cat, price, desc, specs, badge, stock)
                    },
                    onSaveProduct = { viewModel.saveProduct() },
                    onDeleteProduct = { product -> viewModel.deleteProduct(product) },
                    onSaveSettings = { wa, call, email, addr, ref, pin ->
                        viewModel.saveCompanySettings(wa, call, email, addr, ref, pin)
                    },
                    onExportQuotesCsv = { viewModel.exportQuotesCsv(context) },
                    onExportProductsCsv = { viewModel.exportProductsCsv(context) }
                )
            }
        }
    }
}
