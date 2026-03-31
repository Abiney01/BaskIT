package com.app.app.Repository

import com.app.app.Models.Offer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OfferRepository : JpaRepository<Offer, Long>