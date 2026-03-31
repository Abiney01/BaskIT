package com.app.baskit.Models

import com.fasterxml.jackson.annotation.JsonIdentityInfo
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "cart")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator::class, property = "cartId")
data class Cart(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    val cartId: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserDetails? = null,

    @Column(nullable = false)
    var lastUpdatedDate: LocalDateTime? = LocalDateTime.now(),

    var address: String? = null,

    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL], orphanRemoval = true)
    var listOfItems: MutableList<ListOfItems>? = null,

    var isActive: Boolean = true

) {
    @PrePersist
    @PreUpdate
    protected fun onUpdate() {
        lastUpdatedDate = LocalDateTime.now()
    }
}