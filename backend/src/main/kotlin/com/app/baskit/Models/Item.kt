package com.app.baskit.Models

import jakarta.persistence.*

@Entity
@Table(name = "item")
class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var itemId: Long? = null
    var itemName: String? = null
    var price: Double? = null

    @ManyToOne
    @JoinColumn(name = "category_id")
    private var category: Category? = null

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private var offer: Offer? = null

    fun getCategory(): Category? {
        return category
    }

    fun setCategory(category: Category?) {
        this.category = category
    }

    fun getOffer(): Offer? {
        return offer
    }

    fun setOffer(offer: Offer?) {
        this.offer = offer
    }
}