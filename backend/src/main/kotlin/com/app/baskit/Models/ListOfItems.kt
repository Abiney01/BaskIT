package com.app.baskit.Models

import jakarta.persistence.*

@Entity
@Table(name = "list_of_items")
data class ListOfItems(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "cart_id")
    var cart: Cart? = null
)