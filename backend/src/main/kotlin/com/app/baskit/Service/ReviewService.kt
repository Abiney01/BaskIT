package com.app.baskit.Service

import com.app.baskit.Models.Review
import com.app.baskit.Repository.ReviewRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReviewService {
    @Autowired
    private val reviewRepository: ReviewRepository? = null

    val allReviews: MutableList<Review?>
        get() = reviewRepository.findAll()

    fun getReviewById(id: Long?): Review {
        return reviewRepository.findById(id)
            .orElseThrow({ RuntimeException("Review not found with ID: " + id) })
    }

    fun createReview(review: Review?): Review {
        return reviewRepository.save(review)
    }

    fun updateReview(id: Long?, review: Review): Review {
        val existingReview = getReviewById(id)
        existingReview.user = review.user
        existingReview.setItem(review.getItem())
        existingReview.ratings = review.ratings
        existingReview.comments = review.comments
        existingReview.reviewDate = review.reviewDate
        return reviewRepository.save(existingReview)
    }

    fun deleteReview(id: Long?) {
        reviewRepository.deleteById(id)
    }
}