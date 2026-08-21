package com.example.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class GmTechRepository(
    private val productDao: ProductDao,
    private val quoteDao: QuoteDao,
    private val settingsDao: CompanySettingsDao
) {
    val allProducts: Flow<List<ProductEntity>> = productDao.getAllProducts()
    val allQuotes: Flow<List<QuoteEntity>> = quoteDao.getAllQuotes()
    val settingsFlow: Flow<CompanySettingsEntity?> = settingsDao.getSettingsFlow()

    suspend fun ensureDataSeeded() = withContext(Dispatchers.IO) {
        val prodCount = productDao.getProductCount()
        if (prodCount == 0) {
            productDao.insertAll(InitialData.INITIAL_PRODUCTS)
        }
        val quoteCount = quoteDao.getQuoteCount()
        if (quoteCount == 0) {
            quoteDao.insertAll(InitialData.INITIAL_QUOTES)
        }
        val currentSettings = settingsDao.getSettings()
        if (currentSettings == null) {
            settingsDao.saveSettings(CompanySettingsEntity())
        }
    }

    // Product actions
    suspend fun insertProduct(product: ProductEntity): Long = withContext(Dispatchers.IO) {
        productDao.insertProduct(product)
    }

    suspend fun updateProduct(product: ProductEntity) = withContext(Dispatchers.IO) {
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: ProductEntity) = withContext(Dispatchers.IO) {
        productDao.deleteProduct(product)
    }

    suspend fun deleteProductById(id: Long) = withContext(Dispatchers.IO) {
        productDao.deleteProductById(id)
    }

    // Quote actions
    suspend fun createQuote(
        clientName: String,
        clientPhone: String,
        clientEmail: String,
        clientCity: String,
        clientAddress: String,
        serviceRequested: String,
        projectDetails: String,
        estimatedBudget: String
    ): QuoteEntity = withContext(Dispatchers.IO) {
        val year = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())
        val randomDigits = (1000 + Random.nextInt(9000)).toString()
        val quoteNumber = "GMT-$year-$randomDigits"

        val quote = QuoteEntity(
            quoteNumber = quoteNumber,
            clientName = clientName.trim(),
            clientPhone = clientPhone.trim(),
            clientEmail = clientEmail.trim(),
            clientCity = clientCity.trim(),
            clientAddress = clientAddress.trim(),
            serviceRequested = serviceRequested.trim(),
            projectDetails = projectDetails.trim(),
            estimatedBudget = if (estimatedBudget.isBlank()) "À déterminer" else estimatedBudget.trim(),
            status = "NOUVEAU",
            createdAt = System.currentTimeMillis()
        )
        val id = quoteDao.insertQuote(quote)
        quote.copy(id = id)
    }

    suspend fun updateQuoteStatus(quoteId: Long, newStatus: String, notes: String? = null) = withContext(Dispatchers.IO) {
        val quotes = quoteDao.getAllQuotes().firstOrNull() ?: emptyList()
        val existing = quotes.find { it.id == quoteId }
        if (existing != null) {
            val updated = existing.copy(
                status = newStatus,
                internalNotes = notes ?: existing.internalNotes
            )
            quoteDao.updateQuote(updated)
        }
    }

    suspend fun deleteQuote(quote: QuoteEntity) = withContext(Dispatchers.IO) {
        quoteDao.deleteQuote(quote)
    }

    // Settings actions
    suspend fun updateSettings(settings: CompanySettingsEntity) = withContext(Dispatchers.IO) {
        settingsDao.saveSettings(settings)
    }

    suspend fun getSettings(): CompanySettingsEntity = withContext(Dispatchers.IO) {
        settingsDao.getSettings() ?: CompanySettingsEntity()
    }

    // WhatsApp Helpers
    fun buildQuoteWhatsAppMessage(quote: QuoteEntity, companySettings: CompanySettingsEntity): String {
        val dateStr = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRENCH).format(Date(quote.createdAt))
        return """
            Bonjour GM TECH Sarl 👋
            
            Je souhaite vous soumettre une nouvelle demande de devis :
            
            📋 *Devis N° :* ${quote.quoteNumber}
            👤 *Client :* ${quote.clientName}
            📞 *Téléphone :* ${quote.clientPhone}
            📧 *Email :* ${quote.clientEmail.ifEmpty { "Non renseigné" }}
            📍 *Ville & Adresse :* ${quote.clientCity} - ${quote.clientAddress.ifEmpty { "À préciser" }}
            
            🛠️ *Service demandé :*
            ${quote.serviceRequested}
            
            📝 *Description du projet :*
            ${quote.projectDetails}
            
            💰 *Budget estimé :* ${quote.estimatedBudget}
            📅 *Date de la demande :* $dateStr
            
            _Message généré automatiquement via l'application mobile GM Tech Sarl._
        """.trimIndent()
    }

    fun buildProductWhatsAppMessage(product: ProductEntity, clientName: String = ""): String {
        return """
            Bonjour GM TECH Sarl 👋
            
            Je suis intéressé(e) par l'article suivant dans votre catalogue :
            
            🛒 *Produit :* ${product.name}
            🏷️ *Catégorie :* ${product.category}
            💲 *Prix :* ${product.price} ${product.currency}
            📦 *Disponibilité :* ${if (product.inStock) "En Stock" else "Sur commande"}
            ${if (clientName.isNotBlank()) "👤 *Nom :* $clientName" else ""}
            
            Pourriez-vous me fournir plus de détails sur la disponibilité et les modalités de livraison/installation ?
            
            Merci !
        """.trimIndent()
    }

    fun openWhatsApp(context: Context, phoneNumber: String, message: String) {
        val cleanNumber = phoneNumber.replace("+", "").replace(" ", "").replace("-", "")
        val encodedMessage = try {
            URLEncoder.encode(message, "UTF-8")
        } catch (e: Exception) {
            message
        }
        val uri = Uri.parse("https://wa.me/$cleanNumber?text=$encodedMessage")
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback to general browser or dialer
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=$cleanNumber&text=$encodedMessage")).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            try {
                context.startActivity(webIntent)
            } catch (ex: Exception) {
                // Fallback to phone dialer
                val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanNumber")).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(callIntent)
            }
        }
    }

    fun openPhoneCall(context: Context, phoneNumber: String) {
        val cleanNumber = phoneNumber.replace(" ", "").replace("-", "")
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$cleanNumber")).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        try {
            context.startActivity(intent)
        } catch (_: Exception) {}
    }

    // CSV Exports
    fun exportQuotesToCsv(quotes: List<QuoteEntity>): String {
        val sb = StringBuilder()
        sb.append("NumeroDevis,Date,NomClient,Telephone,Email,Ville,Adresse,Service,Description,BudgetEstime,Statut,NotesInternes\n")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        for (q in quotes) {
            val dateStr = dateFormat.format(Date(q.createdAt))
            val cleanDetails = q.projectDetails.replace("\"", "\"\"").replace("\n", " ")
            val cleanNotes = q.internalNotes.replace("\"", "\"\"").replace("\n", " ")
            sb.append("\"${q.quoteNumber}\",")
            sb.append("\"$dateStr\",")
            sb.append("\"${q.clientName.replace("\"", "\"\"")}\",")
            sb.append("\"${q.clientPhone}\",")
            sb.append("\"${q.clientEmail}\",")
            sb.append("\"${q.clientCity}\",")
            sb.append("\"${q.clientAddress}\",")
            sb.append("\"${q.serviceRequested.replace("\"", "\"\"")}\",")
            sb.append("\"$cleanDetails\",")
            sb.append("\"${q.estimatedBudget}\",")
            sb.append("\"${q.status}\",")
            sb.append("\"$cleanNotes\"\n")
        }
        return sb.toString()
    }

    fun exportProductsToCsv(products: List<ProductEntity>): String {
        val sb = StringBuilder()
        sb.append("ID,Nom,Categorie,Prix,Devise,EnStock,Badge,Caracteristiques,Description\n")
        for (p in products) {
            val cleanSpecs = p.specifications.replace("\"", "\"\"").replace("\n", " ")
            val cleanDesc = p.description.replace("\"", "\"\"").replace("\n", " ")
            sb.append("${p.id},")
            sb.append("\"${p.name.replace("\"", "\"\"")}\",")
            sb.append("\"${p.category}\",")
            sb.append("${p.price},")
            sb.append("\"${p.currency}\",")
            sb.append("${if (p.inStock) "Oui" else "Non"},")
            sb.append("\"${p.badge}\",")
            sb.append("\"$cleanSpecs\",")
            sb.append("\"$cleanDesc\"\n")
        }
        return sb.toString()
    }

    fun shareCsvContent(context: Context, title: String, csvContent: String, filename: String) {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, csvContent)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val shareIntent = Intent.createChooser(sendIntent, title).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(shareIntent)
    }
}
