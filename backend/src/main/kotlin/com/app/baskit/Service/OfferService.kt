package com.app.app.Services

import com.app.app.Models.Offer
import com.app.app.Repository.OfferRepository
import org.springframework.stereotype.Service

@Service
class OfferService(private val offerRepository: OfferRepository) {

    fun getAllOffers(): List<Offer> = offerRepository.findAll()

    fun getOfferById(id: Long): Offer =
        offerRepository.findById(id)
            .orElseThrow { RuntimeException("Offer not found with ID: $id") }

    fun createOffer(offer: Offer): Offer = offerRepository.save(offer)

    fun updateOffer(id: Long, offer: Offer): Offer {
        val existing = getOfferById(id)
        existing.minimumOrderAmount = offer.minimumOrderAmount
        existing.validFrom = offer.validFrom
        existing.validTill = offer.validTill
        existing.discountPercentage = offer.discountPercentage
        return offerRepository.save(existing)
    }

    fun deleteOffer(id: Long) = offerRepository.deleteById(id)
}