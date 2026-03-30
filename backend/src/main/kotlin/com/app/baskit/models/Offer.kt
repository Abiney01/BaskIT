package com.app.app.Models

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "offer")
data class Offer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val offerId: Long? = null,

    var minimumOrderAmount: Double? = null,
    var validFrom: LocalDate? = null,
    var validTill: LocalDate? = null,
    var discountPercentage: Double? = null
)