package com.app.baskit.Service

import com.app.baskit.Models.Cart
import com.app.baskit.Models.Orders
import com.app.baskit.Repository.OrderRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
class OrderService @Autowired constructor(
    private val orderRepository: OrderRepository,
    private val userDetailsService: UserDetailsService,
    private val cartService: CartService
) {

    init {
        println("✅ OrderService Initialized!")
        println("✅ Is userDetailsService NULL? ${userDetailsService == null}")
        println("✅ Is cartService NULL? ${cartService == null}")
    }

    fun getAllOrders(): List<Orders> {
        return orderRepository.findAll()
    }

    fun getOrderById(id: Long): Orders {
        return orderRepository.findById(id)
            .orElseThrow { RuntimeException("Order not found with ID: $id") }
    }

    @Transactional
    fun createOrder(order: Orders): Orders {
        println("📦 Received Order Data: $order")

        // Validate input
        if (order.user == null || order.user?.userId == null) {
            throw RuntimeException("⚠️ Order's user field is NULL or has no ID!")
        }
        if (order.cart == null || order.cart?.cartId == null) {
            throw RuntimeException("⚠️ Order's cart field is NULL or has no ID!")
        }

        // Fetch existing user and cart from database
        val user = userDetailsService.getUserById(order.user!!.userId!!)
        val oldCart = cartService.getCartById(order.cart!!.cartId!!)

        if (user == null || oldCart == null) {
            throw RuntimeException("❌ User or Cart not found!")
        }

        // Set existing cart and user
        order.user = user
        order.cart = oldCart
        order.orderStatus = "PLACED"

        // Save the order
        val savedOrder = orderRepository.save(order)
        println("✅ Order saved successfully with ID: ${savedOrder.orderId}")

        // Create a new cart for the user
        val newCart = Cart(
            user = user,
            listOfItems = mutableListOf(),
            lastUpdatedDate = LocalDateTime.now()
        )

        // Save the new cart
        val createdCart = cartService.saveCart(newCart)
        println("🛒 New cart created with ID: ${createdCart.cartId}")

        return savedOrder
    }

    @Transactional
    fun updateOrder(id: Long, orders: Orders): Orders {
        val existingOrder = getOrderById(id)
        existingOrder.user = orders.user
        existingOrder.cart = orders.cart
        existingOrder.orderStatus = orders.orderStatus
        existingOrder.payment = orders.payment
        existingOrder.estimatedDeliveryTime = orders.estimatedDeliveryTime
        existingOrder.deliveryAgent = orders.deliveryAgent
        return orderRepository.save(existingOrder)
    }

    @Transactional
    fun deleteOrder(id: Long) {
        orderRepository.deleteById(id)
    }
}
