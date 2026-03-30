package com.app.baskit.Controller

import com.app.baskit.Models.Review
import com.app.baskit.Service.ReviewService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reviews")
class ReviewController {
    @Autowired
    private val reviewService: ReviewService? = null

    @get:GetMapping
    val allReviews: MutableList<Review?>
        get() = reviewService.getAllReviews()

    @GetMapping("/{id}")
    fun getReviewById(@PathVariable id: Long?): ResponseEntity<Review?> {
        val review: Review? = reviewService.getReviewById(id)
        return ResponseEntity.ok<Review?>(review!!)
    }

    @PostMapping
    fun createReview(@RequestBody review: Review?): ResponseEntity<Review?> {
        val newReview: Review? = reviewService.createReview(review)
        return ResponseEntity.ok<Review?>(newReview!!)
    }

    @PutMapping("/{id}")
    fun updateReview(@PathVariable id: Long?, @RequestBody review: Review?): ResponseEntity<Review?> {
        val updatedReview: Review? = reviewService.updateReview(id, review)
        return ResponseEntity.ok<Review?>(updatedReview!!)
    }

    @DeleteMapping("/{id}")
    fun deleteReview(@PathVariable id: Long?): ResponseEntity<Void?> {
        reviewService.deleteReview(id)
        return ResponseEntity.noContent().build<Void?>()
    }
}