package com.example.data

object InitialData {
    val SERVICES = listOf(
        ServiceModel(
            id = 1,
            title = "Système Solaire Photovoltaïque",
            shortDesc = "Installations complètes onduleurs, batteries lithium & panneaux solaires haute efficacité.",
            fullDesc = "Conception et pose de systèmes solaires autonomes (Off-Grid) et hybrides pour résidences, entreprises et industries. Réduisez ou éliminez vos factures d'électricité grâce à une énergie propre, ininterrompue et pérenne.",
            category = "Solaire",
            features = listOf(
                "Dimensionnement sur mesure selon vos consommations",
                "Onduleurs hybrides intelligents (Victron, Growatt, Deye)",
                "Batteries Lithium LiFePO4 longue durée (6000+ cycles)",
                "Protection parafoudre et raccordement sécurisé",
                "Suivi et monitoring à distance sur smartphone"
            ),
            iconName = "solar_power"
        ),
        ServiceModel(
            id = 2,
            title = "Construction et Fourniture de Lampadaires Solaires",
            shortDesc = "Éclairage public et privé solaire tout-en-un ou séparé avec mât métallique.",
            fullDesc = "Fourniture, fabrication de mâts métalliques et installation de lampadaires solaires haute puissance pour voiries, parkings, concessions minières, résidences privées et cours d'usines.",
            category = "Solaire",
            features = listOf(
                "Lampadaires LED haute luminosité (50W à 300W)",
                "Capteurs crépusculaires et détecteurs de présence",
                "Autonomie de 3 à 5 nuits consécutives",
                "Fabrication de mâts galvanisés sur mesure",
                "Installation clé en main avec scellement béton"
            ),
            iconName = "streetview"
        ),
        ServiceModel(
            id = 3,
            title = "Forage et Construction de Fontaine d'Eau",
            shortDesc = "Accès à l'eau potable : forage géologique, pompe solaire/électrique et château d'eau.",
            fullDesc = "Étude hydrogéologique, forage de puits d'eau profonde, installation de pompes immergées solaires ou électriques, construction de cuves et bornes fontaines communautaires ou privées.",
            category = "Hydraulique",
            features = listOf(
                "Étude géophysique préalable du sous-sol",
                "Forage tubé en PVC alimentaire haute résistance",
                "Pompes solaires immergées basse consommation",
                "Construction de châteaux d'eau et citernes",
                "Systèmes de filtration et traitement de l'eau"
            ),
            iconName = "water_drop"
        ),
        ServiceModel(
            id = 4,
            title = "Électricité de Bâtiment",
            shortDesc = "Câblage résidentiel, commercial et industriel conforme aux normes de sécurité.",
            fullDesc = "Travaux d'installations électriques neuves, réhabilitation, pose de tableaux de distribution, mise à la terre, éclairage LED architectural et sécurisation contre les surtensions.",
            category = "Électricité",
            features = listOf(
                "Schémas électriques et dimensionnement des charges",
                "Tableaux modulaires avec disjoncteurs différentiels",
                "Système de mise à la terre certifié",
                "Éclairage économique et domotique",
                "Audit et mise en conformité des installations anciennes"
            ),
            iconName = "bolt"
        ),
        ServiceModel(
            id = 5,
            title = "Climatisation Générale",
            shortDesc = "Installation, entretien préventif et dépannage de climatiseurs Split, Inverter et VRV.",
            fullDesc = "Solutions de confort thermique pour bureaux, hôpitaux, résidences et commerces. Installation de climatiseurs Inverter à haute efficacité énergétique et contrats de maintenance régulière.",
            category = "Climatisation",
            features = listOf(
                "Climatiseurs Split, Cassette et gainables",
                "Technologie Inverter (jusqu'à 40% d'économie d'énergie)",
                "Recharge en gaz écologique R410A / R32",
                "Nettoyage chimique et décontamination des filtres",
                "Dépannage d'urgence 7j/7"
            ),
            iconName = "ac_unit"
        ),
        ServiceModel(
            id = 6,
            title = "Plomberie Sanitaire et Industrielle",
            shortDesc = "Réseau d'adduction, surpresseurs, évacuation et sanitaires modernes.",
            fullDesc = "Installation complète de plomberie sanitaire, pose de surpresseurs automatiques, chauffe-eau solaires ou électriques, tuyauterie PPR/multicouche et assainissement.",
            category = "Plomberie",
            features = listOf(
                "Tuyauterie encastrée et apparente (PPR, Cuivre, PEHD)",
                "Installation de surpresseurs et ballons tampons",
                "Chauffe-eau solaires haute performance",
                "Pose d'équipements sanitaires et robinetterie moderne",
                "Détection et réparation de fuites non destructives"
            ),
            iconName = "plumbing"
        ),
        ServiceModel(
            id = 7,
            title = "Maintenance de Groupes Électrogènes",
            shortDesc = "Entretien, dépannage et couplage de groupes électrogènes Diesel et Essence.",
            fullDesc = "Service complet pour générateurs de toutes puissances (5 kVA à 1000 kVA). Entretien régulier (vidange, filtres), inversion automatique de source (ATS/Inverseur) et synchronisation.",
            category = "Énergie",
            features = listOf(
                "Maintenance préventive périodique programmée",
                "Installation d'inverseurs de source automatiques (ATS)",
                "Diagnostic électronique et révision moteur",
                "Fourniture de pièces de rechange d'origine",
                "Assistance technique d'urgence 24/7"
            ),
            iconName = "power"
        ),
        ServiceModel(
            id = 8,
            title = "Construction Métallique",
            shortDesc = "Hangars, charpentes, pylônes solaires, grilles de sécurité et portails.",
            fullDesc = "Étude, découpe, soudure et érection de structures métalliques robustes. Confection de bâtis pour supports de panneaux solaires, garde-corps, portes blindées et charpentes industrielles.",
            category = "Structure",
            features = listOf(
                "Bâtis et structures porteuses pour panneaux solaires",
                "Charpentes métalliques pour hangars et toitures",
                "Grilles de protection, antivols et portes blindées",
                "Pylônes pour caméras et antennes relais",
                "Traitement anticorrosion et peinture industrielle"
            ),
            iconName = "handyman"
        ),
        ServiceModel(
            id = 9,
            title = "Caméras de Surveillance & Sécurité",
            shortDesc = "Systèmes CCTV IP, vision nocturne, alertes IA et contrôle à distance.",
            fullDesc = "Protection de vos biens et de votre famille par l'installation de caméras de vidéosurveillance haute définition (Full HD / 4K), enregistreurs NVR/DVR, alarmes anti-intrusion et contrôle d'accès biométrique.",
            category = "Sécurité",
            features = listOf(
                "Caméras IP haute résolution avec vision nocturne couleur",
                "Visualisation en direct sur smartphone partout dans le monde",
                "Détection intelligente d'humains et véhicules par IA",
                "Stockage local sécurisé sur disque dur dédié",
                "Interphone vidéo et contrôle d'accès"
            ),
            iconName = "videocam"
        ),
        ServiceModel(
            id = 10,
            title = "Vente des Matériels & Équipements",
            shortDesc = "Vente d'équipements solaires, câblages, disjoncteurs, climatiseurs et outillage.",
            fullDesc = "Distribution de matériels de grandes marques certifiées aux meilleurs prix du marché : panneaux solaires monocristallins, régulateurs MPPT, câbles solaires, outillage professionnel et accessoires.",
            category = "Commerce",
            features = listOf(
                "Panneaux solaires Tier-1 garantis 25 ans",
                "Batteries GEL et Lithium avec garantie fabricant",
                "Accessoires de fixation en aluminium inoxydable",
                "Disjoncteurs DC/AC et parasurtensions certifiés",
                "Livraison rapide sur site ou retrait au bureau GM Tech"
            ),
            iconName = "shopping_bag"
        )
    )

    val INITIAL_PRODUCTS = listOf(
        ProductEntity(
            id = 1,
            name = "Panneau Solaire Monocristallin 550W Tier-1",
            category = "Solaire",
            price = 145.0,
            currency = "USD",
            description = "Module solaire haute performance half-cut cell avec rendement de 21.5%, idéal pour toitures résidentielles et commerciales.",
            specifications = "Puissance: 550W | Rendement: 21.5% | Cadre: Aluminium anodisé | Garantie: 25 ans rendement",
            inStock = true,
            badge = "Bestseller",
            imageUrl = "",
            rating = 4.9f
        ),
        ProductEntity(
            id = 2,
            name = "Onduleur Hybride Solaire 5kVA / 48V MPPT",
            category = "Solaire",
            price = 680.0,
            currency = "USD",
            description = "Onduleur chargeur tout-en-un avec régulateur MPPT 80A intégré, compatible batterie Lithium et réseau électrique / groupe.",
            specifications = "Puissance: 5000VA / 5000W | Tension: 48V DC | MPPT: 120-450V | Écran LCD tactile & WiFi",
            inStock = true,
            badge = "Populaire",
            imageUrl = "",
            rating = 4.8f
        ),
        ProductEntity(
            id = 3,
            name = "Batterie Lithium LiFePO4 48V 100Ah (5.12 kWh)",
            category = "Solaire",
            price = 1150.0,
            currency = "USD",
            description = "Batterie de stockage au phosphate de fer lithium haute sécurité avec BMS intelligent intégré et 6000 cycles à 80% DOD.",
            specifications = "Capacité: 5.12 kWh (100Ah/51.2V) | Cycles: >6000 | BMS: Intégré avec CAN/RS485 | Poids: 45 kg",
            inStock = true,
            badge = "Top Qualité",
            imageUrl = "",
            rating = 5.0f
        ),
        ProductEntity(
            id = 4,
            name = "Lampadaire Solaire Tout-en-Un 150W LED",
            category = "Solaire",
            price = 120.0,
            currency = "USD",
            description = "Luminaire solaire autonome avec panneau intégré, batterie lithium et détecteur de mouvement pour cours et voiries.",
            specifications = "Puissance: 150W LED | Flux: 15000 Lumens | Batterie: LiFePO4 30Ah | Indice: IP65 étanche",
            inStock = true,
            badge = "Éco",
            imageUrl = "",
            rating = 4.7f
        ),
        ProductEntity(
            id = 5,
            name = "Climatiseur Split Inverter 12000 BTU A+++",
            category = "Climatisation",
            price = 390.0,
            currency = "USD",
            description = "Climatiseur mural ultra silencieux à très faible consommation électrique avec filtration antibactérienne.",
            specifications = "Capacité: 1.5 CV (12000 BTU) | Gaz: R410A / R32 | Technologie: Dual Inverter | Silencieux: 19 dB",
            inStock = true,
            badge = "Économie Énergie",
            imageUrl = "",
            rating = 4.8f
        ),
        ProductEntity(
            id = 6,
            name = "Kit 4 Caméras IP 5MP avec NVR 1To & Câblage",
            category = "Sécurité",
            price = 320.0,
            currency = "USD",
            description = "Système de surveillance complet PoE avec détection humaine par IA, vision nocturne infrarouge et application mobile.",
            specifications = "Résolution: 5 Mégapixels 2K | NVR: 4 Voies PoE + 1To HDD | Vision nocturne: 30m | App: Android & iOS",
            inStock = true,
            badge = "Kit Complet",
            imageUrl = "",
            rating = 4.9f
        ),
        ProductEntity(
            id = 7,
            name = "Pompe Solaire Immergée 3 Pouces Haute Pression",
            category = "Hydraulique",
            price = 450.0,
            currency = "USD",
            description = "Pompe submersible sans balais en acier inoxydable pour puits profonds et forages, alimentée directement par panneaux solaires.",
            specifications = "Débit: 3.5 m³/h | Hauteur max: 80 mètres | Moteur: Brushless DC 72V 750W | Corps: Inox 304",
            inStock = true,
            badge = "Robuste",
            imageUrl = "",
            rating = 4.8f
        ),
        ProductEntity(
            id = 8,
            name = "Tableau Électrique Pré-équipé 24 Modules",
            category = "Électricité",
            price = 95.0,
            currency = "USD",
            description = "Coffret de distribution avec disjoncteur différentiel 30mA, disjoncteurs divisionnaires et parasurtenseur de tête.",
            specifications = "Modules: 24 (2 rangées) | Différentiel: 40A 30mA Type A | Protection: IP40 | Norme: NF C 15-100",
            inStock = true,
            badge = "Certifié",
            imageUrl = "",
            rating = 4.6f
        ),
        ProductEntity(
            id = 9,
            name = "Groupe Électrogène Diesel Insonorisé 7.5 kVA",
            category = "Énergie",
            price = 1650.0,
            currency = "USD",
            description = "Générateur diesel silencieux avec démarrage électrique et prise pour inverseur de source automatique ATS.",
            specifications = "Puissance: 7.5 kVA (6.0 kW) | Démarrage: Électrique + Télécommande | Réservoir: 15 Litres | Niveau sonore: 72 dBA",
            inStock = true,
            badge = "Pro",
            imageUrl = "",
            rating = 4.7f
        ),
        ProductEntity(
            id = 10,
            name = "Surpresseur Automatique d'Eau Domestique 1 CV",
            category = "Plomberie",
            price = 160.0,
            currency = "USD",
            description = "Groupe hydrophore automatique avec ballon 24L et pressostat pour maintenir une pression constante dans toute la maison.",
            specifications = "Puissance: 750W (1.0 HP) | Cuve: 24 Litres | Pression max: 5 bars | Auto-amorçante",
            inStock = true,
            badge = "Essentiel",
            imageUrl = "",
            rating = 4.6f
        )
    )

    val INITIAL_QUOTES = listOf(
        QuoteEntity(
            id = 1,
            quoteNumber = "GMT-2026-0042",
            clientName = "Patrick Mulumba",
            clientPhone = "+243812345678",
            clientEmail = "mulumba.p@gmail.com",
            clientCity = "Kinshasa",
            clientAddress = "Gombe, Av. De la Justice",
            serviceRequested = "Système Solaire Photovoltaïque",
            projectDetails = "Installation d'un système solaire autonome 10kVA pour résidence avec 16 panneaux 550W et 2 batteries Lithium 10kWh.",
            estimatedBudget = "8 500 USD",
            status = "EN_COURS",
            createdAt = System.currentTimeMillis() - 86400000L * 2,
            internalNotes = "Visite technique effectuée hier. Devis final en cours de rédaction."
        ),
        QuoteEntity(
            id = 2,
            quoteNumber = "GMT-2026-0043",
            clientName = "Mme Sophie Kalala",
            clientPhone = "+243899887766",
            clientEmail = "sophie.kalala@yahoo.fr",
            clientCity = "Kinshasa",
            clientAddress = "Ngaliema, Quartier Ma Campagne",
            serviceRequested = "Climatisation Générale",
            projectDetails = "Fourniture et installation de 4 climatiseurs Inverter 12000 BTU et 1 climatiseur 18000 BTU pour villa.",
            estimatedBudget = "2 400 USD",
            status = "NOUVEAU",
            createdAt = System.currentTimeMillis() - 86400000L,
            internalNotes = "Client souhaite un rendez-vous le samedi matin."
        ),
        QuoteEntity(
            id = 3,
            quoteNumber = "GMT-2026-0044",
            clientName = "Entreprise AgriCongo Sarl",
            clientPhone = "+243998123456",
            clientEmail = "direction@agricongo.cd",
            clientCity = "Kongo-Central",
            clientAddress = "Kasangulu, Ferme Éco",
            serviceRequested = "Forage et Construction de Fontaine d'Eau",
            projectDetails = "Forage de 65m avec pompe solaire immergée et réservoir de 10 000 Litres pour irrigation et eau potable.",
            estimatedBudget = "6 200 USD",
            status = "VALIDÉ",
            createdAt = System.currentTimeMillis() - 86400000L * 4,
            internalNotes = "Acompte de 50% reçu, équipe technique déployée sur site."
        )
    )

    val GALLERY_PROJECTS = listOf(
        GalleryProject(
            id = 1,
            title = "Centrale Solaire Résidentielle 15 kVA",
            category = "Solaire",
            location = "Kinshasa - Mont Fleury (Ngaliema)",
            year = "2026",
            description = "Installation d'une centrale solaire hybride haut de gamme : 24 panneaux solaires monocristallins 550W, onduleurs Victron en parallèle et 3 batteries Lithium 48V 100Ah. Autonomie électrique totale 24h/24.",
            keyMetric = "100% Autonome • 15 kVA"
        ),
        GalleryProject(
            id = 2,
            title = "Éclairage Public Solaire - 45 Lampadaires",
            category = "Solaire",
            location = "Kinshasa - Boulevard Lumumba",
            year = "2025",
            description = "Fourniture et érection de 45 mâts galvanisés de 8 mètres avec lampadaires solaires LED 200W autonomes pour la sécurisation nocturne des axes routiers et parkings.",
            keyMetric = "45 Mâts solaires • 200W LED"
        ),
        GalleryProject(
            id = 3,
            title = "Forage d'Eau Potable & Pompe Solaire 75m",
            category = "Hydraulique",
            location = "Kinshasa - Maluku",
            year = "2026",
            description = "Réalisation d'un forage profond de 75 mètres avec tubage PVC alimentaire, pompe solaire immergée Grundfos 3.5 m³/h et château d'eau de 10 000 L alimentant 4 bornes fontaines.",
            keyMetric = "75m Profondeur • 10m³ Stockage"
        ),
        GalleryProject(
            id = 4,
            title = "Câblage Électrique et Tableau Industriel",
            category = "Électricité",
            location = "Kinshasa - Limete Industriel",
            year = "2025",
            description = "Conception et câblage complet de l'armoire de distribution électrique d'une usine de transformation, avec inverseur de source automatique 250A et compensation d'énergie réactive.",
            keyMetric = "250A • Protection Intégrale"
        ),
        GalleryProject(
            id = 5,
            title = "Réseau Vidéosurveillance IP 32 Caméras 4K",
            category = "Sécurité",
            location = "Kinshasa - Centre d'affaires Gombe",
            year = "2026",
            description = "Déploiement d'une infrastructure de sécurité complète avec 32 caméras dôme et tube 4K, serveur NVR 64TB et poste de supervision de sécurité avec accès distant sécurisé.",
            keyMetric = "32 Caméras 4K • Salle de contrôle"
        ),
        GalleryProject(
            id = 6,
            title = "Climatisation Centrale & VMC Immeuble R+4",
            category = "Climatisation",
            location = "Kinshasa - Avenue Wangata",
            year = "2026",
            description = "Installation d'un système multi-split VRV Inverter pour un immeuble de bureaux de 4 étages avec régulation intelligente pièce par pièce et économie d'énergie optimisée.",
            keyMetric = "24 Unités Inverter • Confort Pro"
        )
    )
}
