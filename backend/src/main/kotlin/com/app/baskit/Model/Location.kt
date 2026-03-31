package com.app.baskit.Models

import jakarta.persistence.*

@Entity
@Table(name = "location")
data class Location(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    val locationId: Long? = null,

    var locationName: String? = null
)