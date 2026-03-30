package com.app.baskit.Models

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import jakarta.persistence.*

@Entity
@Table(name = "list_of_items")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "id")
open class ListOfItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "cart_id")
    var cart: Cart? = null

    @Column(nullable = false)
    var quantity: Int = 0   // ✅ no nullable

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "itemId")
    var item: Item? = null
}