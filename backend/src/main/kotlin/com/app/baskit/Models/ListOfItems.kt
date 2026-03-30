package com.app.baskit.Models

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators.PropertyGenerator
import jakarta.persistence.*


@Entity
@Table(name = "list_of_items")
@JsonIdentityInfo(generator = PropertyGenerator::class, property = "id")
class ListOfItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "cart_id")
    var cart: Cart? = null

    @Column(nullable = false)
    var quantity: Int? = null

    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "itemId")
    var item: Item? = null
}