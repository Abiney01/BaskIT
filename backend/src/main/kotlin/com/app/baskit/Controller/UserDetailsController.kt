package com.app.app.Controller

import com.app.app.Models.UserDetails
import com.app.app.Services.UserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserDetailsController {
    @Autowired
    private val userDetailsService: UserDetailsService? = null

    @get:GetMapping
    val allUsers: MutableList<UserDetails>
        get() = userDetailsService.getAllUsers()

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long?): ResponseEntity<UserDetails?> {
        val user: UserDetails? = userDetailsService.getUserById(id)
        return ResponseEntity.ok(user)
    }

    @PostMapping
    fun createUser(@RequestBody userDetails: UserDetails?): ResponseEntity<UserDetails?> {
        val newUser: UserDetails? = userDetailsService.createUser(userDetails)
        return ResponseEntity.ok(newUser)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long?, @RequestBody userDetails: UserDetails?): ResponseEntity<UserDetails?> {
        val updatedUser: UserDetails? = userDetailsService.updateUser(id, userDetails)
        return ResponseEntity.ok(updatedUser)
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long?): ResponseEntity<Void?> {
        userDetailsService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}