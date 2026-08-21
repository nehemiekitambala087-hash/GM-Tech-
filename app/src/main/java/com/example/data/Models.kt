package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val category: String,
    val price: Double,
    val currency: String = "USD",
    val description: String,
    val specifications: String, // Comma-separated or bullet list
    val inStock: Boolean = true,
    val badge: String = "", // e.g. "Populaire", "Nouveau", "Promo"
    val imageUrl: String = "",
    val rating: Float = 4.8f
)

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val quoteNumber: String,
    val clientName: String,
    val clientPhone: String,
    val clientEmail: String = "",
    val clientCity: String = "Kinshasa",
    val clientAddress: String = "",
    val serviceRequested: String,
    val projectDetails: String,
    val estimatedBudget: String = "À déterminer",
    val status: String = "NOUVEAU", // NOUVEAU, EN_COURS, VALIDÉ, REJETÉ, TERMINÉ
    val createdAt: Long = System.currentTimeMillis(),
    val internalNotes: String = ""
)

@Entity(tableName = "company_settings")
data class CompanySettingsEntity(
    @PrimaryKey val id: Int = 1,
    val companyName: String = "GM TECH Sarl",
    val slogan: String = "Adoptez la technologie qui vous libère des hausses de prix.",
    val phoneCall: String = "+243 994 116 034",
    val phoneWhatsApp: String = "+243994116034",
    val email: String = "contact@gmtech-sarl.com",
    val address: String = "Avenue Kabinda au croisement de l'Avenue Wangata",
    val refLocation: String = "Réf Salle de fête Véronique (4e niveau)",
    val adminPin: String = "1234"
)

data class ServiceModel(
    val id: Int,
    val title: String,
    val shortDesc: String,
    val fullDesc: String,
    val category: String,
    val features: List<String>,
    val iconName: String
)

data class GalleryProject(
    val id: Int,
    val title: String,
    val category: String,
    val location: String,
    val year: String,
    val description: String,
    val keyMetric: String
)
