package com.app.baskit.Repository

import com.app.baskit.Models.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CartRepository : JpaRepository<Cart?, Long?> {
    fun findByUser_UserId(userId: Long?): MutableList<Cart?>?
}