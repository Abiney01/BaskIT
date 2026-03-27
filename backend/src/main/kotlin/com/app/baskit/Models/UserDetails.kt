package com.app.baskit.Models

import jakarta.persistence.*

@Entity
@Table(name = "user_details")
data class UserDetails(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val userId: Long? = null,

    @Column(name = "phone_no")
    var phoneNo: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @Column(name = "password")
    var password: String? = null,

    @ManyToOne
    @JoinColumn(name = "location_id")
    var location: Location? = null,

    @Column(name = "address")
    var address: String? = null

)