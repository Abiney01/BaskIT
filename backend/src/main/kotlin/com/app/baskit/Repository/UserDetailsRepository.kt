package com.app.app.Repository

import com.app.app.Models.UserDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDetailsRepository : JpaRepository<UserDetails?, Long?> {
    fun findByEmail(email: String?): UserDetails?
}