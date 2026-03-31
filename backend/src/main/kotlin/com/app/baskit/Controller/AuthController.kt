package com.app.app.Controller

import com.app.app.Models.UserDetails
import com.app.app.Services.UserDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = ["http://localhost:3000"])
class AuthController(private val userDetailsService: UserDetailsService) {

    @PostMapping("/login")
    fun login(@RequestBody userDetails: UserDetails): ResponseEntity<*> {
        val user = userDetailsService.getUserByEmail(userDetails.email ?: "")
        return if (user != null && user.password == userDetails.password) {
            ResponseEntity.ok(
                mapOf(
                    "message" to "Login successful",
                    "userId" to user.userId,
                    "email" to user.email,
                    "name" to user.name,
                    "locationId" to user.location?.locationId,
                    "address" to user.address
                )
            )
        } else {
            ResponseEntity.status(401).body(mapOf("error" to "Invalid credentials"))
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody userDetails: UserDetails): ResponseEntity<*> {
        return try {
            if (userDetails.email == null || userDetails.password == null) {
                return ResponseEntity.status(400)
                    .body(mapOf("error" to "Email and password are required"))
            }
            if (userDetailsService.getUserByEmail(userDetails.email) != null) {
                return ResponseEntity.status(400)
                    .body(mapOf("error" to "Email already registered"))
            }
            val newUser = userDetailsService.createUser(userDetails)
            ResponseEntity.status(201)
                .body(mapOf("message" to "User registered successfully", "userId" to newUser.userId))
        } catch (e: Exception) {
            ResponseEntity.badRequest().build<Any>()
        }
    }
}