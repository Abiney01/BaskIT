package com.app.app.Models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "payment")
data class Payment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    val paymentId: Long? = null,

    var orderAmount: Double? = null,
    var paymentStatus: String? = null,
    var paymentMethod: String? = null,
    var paymentDate: LocalDateTime? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    var order: Orders? = null
)