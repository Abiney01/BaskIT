package com.app.baskit.Models

import jakarta.persistence.*


@Entity
@Table(name = "storage_details")
class StorageDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @ManyToOne
    @JoinColumn(name = "store_id")
    var store: StoreDetails? = null

    @ManyToOne
    @JoinColumn(name = "item_id")
    var item: Item? = null

    var stockAvailability: Int? = null
}