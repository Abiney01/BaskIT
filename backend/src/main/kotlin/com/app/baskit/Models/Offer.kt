package com.app.baskit.Models

import jakarta.persistence.*
import java.time.LocalDate


@Entity
@Table(name = "offer")
class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var offerId: Long? = null

    var minimumOrderAmount: Double? = null
    var validFrom: LocalDate? = null
    var validTill: LocalDate? = null
    var discountPercentage: Double? = null
}