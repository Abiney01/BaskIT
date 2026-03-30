package com.app.baskit.Models

import jakarta.persistence.*
import com.app.app.Models.Category
import com.app.baskit.Models.Offer
@Entity
@Table(name = "item")
open class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var itemId: Long? = null

    var itemName: String? = null

    var price: Double? = null

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category? = null

    @ManyToOne
    @JoinColumn(name = "offer_id")
    var offer: Offer? = null
}