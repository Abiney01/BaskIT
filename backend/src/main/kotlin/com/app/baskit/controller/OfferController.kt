package com.app.app.Controller

import com.app.app.Models.Offer
import com.app.app.Services.OfferService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/offers")
@CrossOrigin(origins = ["http://localhost:3000"])
class OfferController(private val offerService: OfferService) {

    @GetMapping
    fun getAllOffers(): List<Offer> = offerService.getAllOffers()

    @GetMapping("/{id}")
    fun getOfferById(@PathVariable id: Long): ResponseEntity<Offer> =
        ResponseEntity.ok(offerService.getOfferById(id))

    @PostMapping
    fun createOffer(@RequestBody offer: Offer): ResponseEntity<Offer> =
        ResponseEntity.ok(offerService.createOffer(offer))

    @PutMapping("/{id}")
    fun updateOffer(@PathVariable id: Long, @RequestBody offer: Offer): ResponseEntity<Offer> =
        ResponseEntity.ok(offerService.updateOffer(id, offer))

    @DeleteMapping("/{id}")
    fun deleteOffer(@PathVariable id: Long): ResponseEntity<Void> {
        offerService.deleteOffer(id)
        return ResponseEntity.noContent().build()
    }
}