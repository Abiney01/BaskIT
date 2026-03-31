package com.app.baskit.Models

import jakarta.persistence.*


@Entity
@Table(name = "store_details")
class StoreDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var storeId: Long? = null

    var storeName: String? = null
    var storeContactInfo: Long? = null

    @ManyToOne
    @JoinColumn(name = "location_id")
    var location: Location? = null
}