package com.app.app.Services

import com.app.app.Models.Payment
import com.app.app.Repository.PaymentRepository
import org.springframework.stereotype.Service

@Service
class PaymentService(private val paymentRepository: PaymentRepository) {

    fun getAllPayments(): List<Payment> = paymentRepository.findAll()

    fun getPaymentById(id: Long): Payment =
        paymentRepository.findById(id)
            .orElseThrow { RuntimeException("Payment not found with ID: $id") }

    fun createPayment(payment: Payment): Payment = paymentRepository.save(payment)

    fun updatePayment(id: Long, payment: Payment): Payment {
        val existing = getPaymentById(id)
        existing.orderAmount = payment.orderAmount
        existing.paymentStatus = payment.paymentStatus
        existing.paymentMethod = payment.paymentMethod
        existing.paymentDate = payment.paymentDate
        return paymentRepository.save(existing)
    }

    fun deletePayment(id: Long) = paymentRepository.deleteById(id)
}