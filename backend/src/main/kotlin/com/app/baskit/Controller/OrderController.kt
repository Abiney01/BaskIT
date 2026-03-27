package com.app.baskit.Controller

import com.app.baskit.Models.Orders
import com.app.baskit.Models.Payment
import com.app.baskit.Service.CartService
import com.app.baskit.Service.OrderService
import com.app.baskit.Service.PaymentService
import com.app.baskit.Service.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = ["http://localhost:3000"])
class OrderController @Autowired constructor(
    private val orderService: OrderService,
    private val paymentService: PaymentService,
    private val userDetailsService: UserDetailsService,
    private val cartService: CartService
) {

    @GetMapping
    fun getAllOrders(): List<Orders> {
        return orderService.getAllOrders()
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id: Long): ResponseEntity<Orders> {
        val orders = orderService.getOrderById(id)
        return ResponseEntity.ok(orders)
    }

    @PostMapping
    fun placeOrder(@RequestBody orderData: Map<String, Any>): ResponseEntity<*> {
        println("📦 Received Order Data: $orderData")

        return try {
            val userId = orderData["userId"].toString().toLong()
            val cartId = orderData["cartId"].toString().toLong()
            val paymentMethod = orderData["paymentMethod"].toString()
            val totalAmount = orderData["totalAmount"].toString().toDouble()

            println("🔍 Fetching user with ID: $userId")
            println("🔍 Fetching cart with ID: $cartId")

            val user = userDetailsService.getUserById(userId)
            val cart = cartService.getCartById(cartId)

            if (user == null) {
                println("❌ User not found!")
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found!")
            }

            if (cart == null) {
                println("❌ Cart not found!")
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cart not found!")
            }

            println("✅ User and Cart fetched successfully!")

            // Create Payment
            val payment = Payment(
                orderAmount = totalAmount,
                paymentMethod = paymentMethod,
                paymentStatus = if (paymentMethod == "cod") "Pending" else "Paid",
                paymentDate = LocalDateTime.now()
            )

            val savedPayment = paymentService.createPayment(payment)
            println("✅ Payment saved successfully with ID: ${savedPayment.paymentId}")

            // Create Order
            val order = Orders(
                user = user,
                cart = cart,
                payment = savedPayment,
                name = orderData["name"].toString(),
                phoneNumber = orderData["phoneNumber"].toString(),
                address = orderData["address"].toString(),
                totalAmount = totalAmount,
                orderStatus = "Pending"
            )

            val savedOrder = orderService.createOrder(order)
            println("✅ Order saved successfully with ID: ${savedOrder.orderId}")

            // Update Payment with Order
            savedPayment.orders = savedOrder
            paymentService.updatePayment(savedPayment.paymentId!!, savedPayment)

            ResponseEntity.status(HttpStatus.CREATED).body(savedOrder)

        } catch (e: Exception) {
            println("❌ Order Placement Error: ${e.message}")
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to place order: ${e.message}")
        }
    }

    @PutMapping("/{id}")
    fun updateOrder(@PathVariable id: Long, @RequestBody orders: Orders): ResponseEntity<Orders> {
        val updatedOrders = orderService.updateOrder(id, orders)
        return ResponseEntity.ok(updatedOrders)
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable id: Long): ResponseEntity<String> {
        orderService.deleteOrder(id)
        return ResponseEntity.ok("Order deleted successfully")
    }
}
