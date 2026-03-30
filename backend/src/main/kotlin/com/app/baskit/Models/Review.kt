package com.app.app.Models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "review")
class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var reviewId: Long? = null

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserDetails? = null

    @ManyToOne
    @JoinColumn(name = "item_id")
    private var item: Item? = null

    var ratings: Int? = null
    var comments: String? = null
    var reviewDate: LocalDateTime? = null

    fun getItem(): Item? {
        return item
    }

    fun setItem(item: Item?) {
        this.item = item
    }
}