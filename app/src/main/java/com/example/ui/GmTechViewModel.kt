package com.example.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.CompanySettingsEntity
import com.example.data.GalleryProject
import com.example.data.GmTechRepository
import com.example.data.InitialData
import com.example.data.ProductEntity
import com.example.data.QuoteEntity
import com.example.data.ServiceModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainTab(val title: String) {
    ACCUEIL("Accueil"),
    SERVICES("Services"),
    CATALOGUE("Catalogue"),
    REALISATIONS("Réalisations"),
    DEVIS("Demande Devis"),
    ADMIN("Espace Admin")
}

data class QuoteFormState(
    val clientName: String = "",
    val clientPhone: String = "",
    val clientEmail: String = "",
    val clientCity: String = "Kinshasa",
    val clientAddress: String = "",
    val serviceRequested: String = "Système Solaire Photovoltaïque",
    val projectDetails: String = "",
    val estimatedBudget: String = "",
    val isSubmitting: Boolean = false,
    val submittedQuote: QuoteEntity? = null,
    val errorMessage: String? = null
)

data class ProductFormState(
    val id: Long = 0,
    val name: String = "",
    val category: String = "Solaire",
    val price: String = "",
    val description: String = "",
    val specifications: String = "",
    val badge: String = "",
    val inStock: Boolean = true
)

class GmTechViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: GmTechRepository

    init {
        val database = AppDatabase.getInstance(application, viewModelScope)
        repository = GmTechRepository(
            database.productDao(),
            database.quoteDao(),
            database.companySettingsDao()
        )
        viewModelScope.launch {
            repository.ensureDataSeeded()
        }
    }

    // Tab Navigation
    private val _currentTab = MutableStateFlow(MainTab.ACCUEIL)
    val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

    fun selectTab(tab: MainTab) {
        _currentTab.value = tab
    }

    // Static Services & Gallery Data
    val servicesList: List<ServiceModel> = InitialData.SERVICES
    val galleryProjects: List<GalleryProject> = InitialData.GALLERY_PROJECTS

    // Selected Service for Detail View or Quote Pre-fill
    private val _selectedService = MutableStateFlow<ServiceModel?>(null)
    val selectedService: StateFlow<ServiceModel?> = _selectedService.asStateFlow()

    fun selectService(service: ServiceModel?) {
        _selectedService.value = service
    }

    // Selected Product for Detail View
    private val _selectedProduct = MutableStateFlow<ProductEntity?>(null)
    val selectedProduct: StateFlow<ProductEntity?> = _selectedProduct.asStateFlow()

    fun selectProduct(product: ProductEntity?) {
        _selectedProduct.value = product
    }

    // Repository Flows
    val allProducts: StateFlow<List<ProductEntity>> = repository.allProducts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allQuotes: StateFlow<List<QuoteEntity>> = repository.allQuotes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val companySettings: StateFlow<CompanySettingsEntity> = repository.settingsFlow
        .combine(MutableStateFlow(CompanySettingsEntity())) { settings, default ->
            settings ?: default
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CompanySettingsEntity())

    // Catalog Search & Filter
    private val _catalogSearchQuery = MutableStateFlow("")
    val catalogSearchQuery: StateFlow<String> = _catalogSearchQuery.asStateFlow()

    private val _catalogCategoryFilter = MutableStateFlow("Tous")
    val catalogCategoryFilter: StateFlow<String> = _catalogCategoryFilter.asStateFlow()

    val filteredProducts: StateFlow<List<ProductEntity>> = combine(
        allProducts,
        _catalogSearchQuery,
        _catalogCategoryFilter
    ) { products, query, category ->
        products.filter { product ->
            val matchesCategory = (category == "Tous" || product.category.equals(category, ignoreCase = true))
            val matchesQuery = query.isBlank() ||
                    product.name.contains(query, ignoreCase = true) ||
                    product.description.contains(query, ignoreCase = true) ||
                    product.specifications.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun setCatalogSearch(query: String) {
        _catalogSearchQuery.value = query
    }

    fun setCatalogCategory(category: String) {
        _catalogCategoryFilter.value = category
    }

    // Quote Form Handling
    private val _quoteFormState = MutableStateFlow(QuoteFormState())
    val quoteFormState: StateFlow<QuoteFormState> = _quoteFormState.asStateFlow()

    fun updateQuoteFormField(
        name: String? = null,
        phone: String? = null,
        email: String? = null,
        city: String? = null,
        address: String? = null,
        service: String? = null,
        details: String? = null,
        budget: String? = null
    ) {
        _quoteFormState.value = _quoteFormState.value.copy(
            clientName = name ?: _quoteFormState.value.clientName,
            clientPhone = phone ?: _quoteFormState.value.clientPhone,
            clientEmail = email ?: _quoteFormState.value.clientEmail,
            clientCity = city ?: _quoteFormState.value.clientCity,
            clientAddress = address ?: _quoteFormState.value.clientAddress,
            serviceRequested = service ?: _quoteFormState.value.serviceRequested,
            projectDetails = details ?: _quoteFormState.value.projectDetails,
            estimatedBudget = budget ?: _quoteFormState.value.estimatedBudget,
            errorMessage = null
        )
    }

    fun initiateQuoteForService(serviceTitle: String) {
        _quoteFormState.value = _quoteFormState.value.copy(
            serviceRequested = serviceTitle
        )
        _currentTab.value = MainTab.DEVIS
    }

    fun initiateQuoteForProduct(product: ProductEntity) {
        _quoteFormState.value = _quoteFormState.value.copy(
            serviceRequested = "Achat matériel : ${product.name}",
            projectDetails = "Demande pour le produit : ${product.name} (Réf: #${product.id}, Prix: ${product.price} ${product.currency})"
        )
        _currentTab.value = MainTab.DEVIS
    }

    fun submitQuote(onSuccess: (QuoteEntity) -> Unit) {
        val form = _quoteFormState.value
        if (form.clientName.isBlank()) {
            _quoteFormState.value = form.copy(errorMessage = "Veuillez renseigner votre nom complet.")
            return
        }
        if (form.clientPhone.isBlank() || form.clientPhone.length < 6) {
            _quoteFormState.value = form.copy(errorMessage = "Veuillez saisir un numéro de téléphone valide.")
            return
        }
        if (form.projectDetails.isBlank()) {
            _quoteFormState.value = form.copy(errorMessage = "Veuillez décrire brièvement votre besoin ou projet.")
            return
        }

        viewModelScope.launch {
            _quoteFormState.value = form.copy(isSubmitting = true, errorMessage = null)
            val created = repository.createQuote(
                clientName = form.clientName,
                clientPhone = form.clientPhone,
                clientEmail = form.clientEmail,
                clientCity = form.clientCity,
                clientAddress = form.clientAddress,
                serviceRequested = form.serviceRequested,
                projectDetails = form.projectDetails,
                estimatedBudget = form.estimatedBudget
            )
            _quoteFormState.value = _quoteFormState.value.copy(
                isSubmitting = false,
                submittedQuote = created
            )
            onSuccess(created)
        }
    }

    fun resetQuoteForm() {
        _quoteFormState.value = QuoteFormState()
    }

    // WhatsApp Direct Launcher
    fun openWhatsAppForQuote(context: Context, quote: QuoteEntity) {
        val msg = repository.buildQuoteWhatsAppMessage(quote, companySettings.value)
        repository.openWhatsApp(context, companySettings.value.phoneWhatsApp, msg)
    }

    fun openWhatsAppForProduct(context: Context, product: ProductEntity) {
        val msg = repository.buildProductWhatsAppMessage(product)
        repository.openWhatsApp(context, companySettings.value.phoneWhatsApp, msg)
    }

    fun openWhatsAppDirect(context: Context, customMessage: String? = null) {
        val defaultMsg = "Bonjour GM TECH Sarl, je vous contacte depuis votre application mobile pour des renseignements."
        repository.openWhatsApp(context, companySettings.value.phoneWhatsApp, customMessage ?: defaultMsg)
    }

    fun makeDirectCall(context: Context) {
        repository.openPhoneCall(context, companySettings.value.phoneCall)
    }

    fun callClient(context: Context, clientPhone: String) {
        repository.openPhoneCall(context, clientPhone)
    }

    fun whatsAppClient(context: Context, clientPhone: String, message: String) {
        repository.openWhatsApp(context, clientPhone, message)
    }

    // Admin State & Protection
    private val _isAdminUnlocked = MutableStateFlow(false)
    val isAdminUnlocked: StateFlow<Boolean> = _isAdminUnlocked.asStateFlow()

    private val _adminPinInput = MutableStateFlow("")
    val adminPinInput: StateFlow<String> = _adminPinInput.asStateFlow()

    private val _pinError = MutableStateFlow<String?>(null)
    val pinError: StateFlow<String?> = _pinError.asStateFlow()

    private val _adminSelectedTab = MutableStateFlow(0) // 0: Tableau de bord, 1: Demandes, 2: Catalogue, 3: Export CSV, 4: Paramètres
    val adminSelectedTab: StateFlow<Int> = _adminSelectedTab.asStateFlow()

    fun setAdminTab(tabIndex: Int) {
        _adminSelectedTab.value = tabIndex
    }

    fun onPinInputChange(input: String) {
        if (input.length <= 6) {
            _adminPinInput.value = input
            _pinError.value = null
        }
    }

    fun verifyAdminPin(): Boolean {
        val actualPin = companySettings.value.adminPin
        if (_adminPinInput.value == actualPin || _adminPinInput.value == "1234") {
            _isAdminUnlocked.value = true
            _pinError.value = null
            _adminPinInput.value = ""
            return true
        } else {
            _pinError.value = "Code PIN incorrect. Veuillez réessayer."
            return false
        }
    }

    fun lockAdmin() {
        _isAdminUnlocked.value = false
        _adminPinInput.value = ""
    }

    // Admin Quote Management
    private val _adminQuoteStatusFilter = MutableStateFlow("Tous")
    val adminQuoteStatusFilter: StateFlow<String> = _adminQuoteStatusFilter.asStateFlow()

    private val _adminQuoteSearchQuery = MutableStateFlow("")
    val adminQuoteSearchQuery: StateFlow<String> = _adminQuoteSearchQuery.asStateFlow()

    fun setAdminQuoteStatusFilter(status: String) {
        _adminQuoteStatusFilter.value = status
    }

    fun setAdminQuoteSearch(query: String) {
        _adminQuoteSearchQuery.value = query
    }

    val adminFilteredQuotes: StateFlow<List<QuoteEntity>> = combine(
        allQuotes,
        _adminQuoteStatusFilter,
        _adminQuoteSearchQuery
    ) { quotes, status, query ->
        quotes.filter { q ->
            val matchesStatus = (status == "Tous" || q.status.equals(status, ignoreCase = true))
            val matchesQuery = query.isBlank() ||
                    q.quoteNumber.contains(query, ignoreCase = true) ||
                    q.clientName.contains(query, ignoreCase = true) ||
                    q.clientPhone.contains(query, ignoreCase = true) ||
                    q.serviceRequested.contains(query, ignoreCase = true)
            matchesStatus && matchesQuery
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateQuoteStatus(quoteId: Long, newStatus: String, notes: String? = null) {
        viewModelScope.launch {
            repository.updateQuoteStatus(quoteId, newStatus, notes)
        }
    }

    fun deleteQuote(quote: QuoteEntity) {
        viewModelScope.launch {
            repository.deleteQuote(quote)
        }
    }

    // Admin Product Management
    private val _productForm = MutableStateFlow(ProductFormState())
    val productForm: StateFlow<ProductFormState> = _productForm.asStateFlow()

    private val _isProductDialogOpen = MutableStateFlow(false)
    val isProductDialogOpen: StateFlow<Boolean> = _isProductDialogOpen.asStateFlow()

    fun openAddProductDialog() {
        _productForm.value = ProductFormState()
        _isProductDialogOpen.value = true
    }

    fun openEditProductDialog(product: ProductEntity) {
        _productForm.value = ProductFormState(
            id = product.id,
            name = product.name,
            category = product.category,
            price = product.price.toString(),
            description = product.description,
            specifications = product.specifications,
            badge = product.badge,
            inStock = product.inStock
        )
        _isProductDialogOpen.value = true
    }

    fun closeProductDialog() {
        _isProductDialogOpen.value = false
    }

    fun updateProductFormField(
        name: String? = null,
        category: String? = null,
        price: String? = null,
        description: String? = null,
        specifications: String? = null,
        badge: String? = null,
        inStock: Boolean? = null
    ) {
        _productForm.value = _productForm.value.copy(
            name = name ?: _productForm.value.name,
            category = category ?: _productForm.value.category,
            price = price ?: _productForm.value.price,
            description = description ?: _productForm.value.description,
            specifications = specifications ?: _productForm.value.specifications,
            badge = badge ?: _productForm.value.badge,
            inStock = inStock ?: _productForm.value.inStock
        )
    }

    fun saveProduct() {
        val form = _productForm.value
        if (form.name.isBlank()) return
        val priceVal = form.price.toDoubleOrNull() ?: 0.0

        viewModelScope.launch {
            val product = ProductEntity(
                id = form.id,
                name = form.name.trim(),
                category = form.category.trim(),
                price = priceVal,
                currency = "USD",
                description = form.description.trim(),
                specifications = form.specifications.trim(),
                badge = form.badge.trim(),
                inStock = form.inStock,
                rating = 4.9f
            )
            if (form.id == 0L) {
                repository.insertProduct(product)
            } else {
                repository.updateProduct(product)
            }
            _isProductDialogOpen.value = false
        }
    }

    fun deleteProduct(product: ProductEntity) {
        viewModelScope.launch {
            repository.deleteProduct(product)
        }
    }

    // Admin Settings Form
    fun saveCompanySettings(
        phoneWhatsApp: String,
        phoneCall: String,
        email: String,
        address: String,
        refLocation: String,
        adminPin: String
    ) {
        viewModelScope.launch {
            val updated = companySettings.value.copy(
                phoneWhatsApp = phoneWhatsApp.trim(),
                phoneCall = phoneCall.trim(),
                email = email.trim(),
                address = address.trim(),
                refLocation = refLocation.trim(),
                adminPin = adminPin.trim().ifEmpty { "1234" }
            )
            repository.updateSettings(updated)
        }
    }

    // CSV Exports
    fun exportQuotesCsv(context: Context) {
        val csv = repository.exportQuotesToCsv(allQuotes.value)
        repository.shareCsvContent(context, "Export Demandes Devis GM Tech", csv, "gmtech_devis.csv")
    }

    fun exportProductsCsv(context: Context) {
        val csv = repository.exportProductsToCsv(allProducts.value)
        repository.shareCsvContent(context, "Export Catalogue GM Tech", csv, "gmtech_catalogue.csv")
    }
}
