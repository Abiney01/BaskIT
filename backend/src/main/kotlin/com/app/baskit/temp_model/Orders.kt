package com.app.baskit.Models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
data class Orders(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val orderId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    var user: UserDetails? = null,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
    var cart: Cart? = null,

    @OneToOne
    @JoinColumn(name = "payment_id", referencedColumnName = "payment_id")
    var payment: Payment? = null,

    var estimatedDeliveryTime: LocalDateTime? = null,

    @ManyToOne
    @JoinColumn(name = "delivery_agent_id", referencedColumnName = "delivery_agent_id")
    var deliveryAgent: DeliveryAgent? = null,

    var name: String? = null,
    var phoneNumber: String? = null,
    var totalAmount: Double? = null,
    var address: String? = null,
    var orderStatus: String? = null

)