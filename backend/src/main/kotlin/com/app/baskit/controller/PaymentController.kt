package com.app.app.Controller

import com.app.app.Models.Payment
import com.app.app.Services.PaymentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = ["http://localhost:3000"])
class PaymentController(private val paymentService: PaymentService) {

    @GetMapping
    fun getAllPayments(): List<Payment> = paymentService.getAllPayments()

    @GetMapping("/{id}")
    fun getPaymentById(@PathVariable id: Long): ResponseEntity<Payment> =
        ResponseEntity.ok(paymentService.getPaymentById(id))

    @PostMapping
    fun createPayment(@RequestBody payment: Payment): ResponseEntity<Payment> =
        ResponseEntity.ok(paymentService.createPayment(payment))

    @PutMapping("/{id}")
    fun updatePayment(@PathVariable id: Long, @RequestBody payment: Payment): ResponseEntity<Payment> =
        ResponseEntity.ok(paymentService.updatePayment(id, payment))

    @DeleteMapping("/{id}")
    fun deletePayment(@PathVariable id: Long): ResponseEntity<Void> {
        paymentService.deletePayment(id)
        return ResponseEntity.noContent().build()
    }
}