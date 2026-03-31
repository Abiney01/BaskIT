package com.app.baskit.Repository

import com.app.baskit.Models.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : JpaRepository<Review?, Long?>