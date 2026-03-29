package com.app.app.Services

import com.app.app.Models.UserDetails
import com.app.app.Repository.UserDetailsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserDetailsService @Autowired constructor(private val userDetailsRepository: UserDetailsRepository) {
    init {
        println("✅ UserDetailsService Initialized by Spring")
    }

    val allUsers: MutableList<UserDetails?>
        get() = userDetailsRepository.findAll()

    fun getUserById(id: Long?): UserDetails {
        return userDetailsRepository.findById(id)
            .orElseThrow({ RuntimeException("User not found with ID: " + id) })
    }

    fun getUserByEmail(email: String?): UserDetails? {
        return userDetailsRepository.findByEmail(email)
    }

    fun createUser(userDetails: UserDetails?): UserDetails {
        println("Saving user: " + userDetails)
        val savedUser: UserDetails = userDetailsRepository.save(userDetails)
        println("Saved user ID: " + savedUser.userId)
        return savedUser
    }


    fun updateUser(id: Long?, userDetails: UserDetails): UserDetails {
        val existingUser = getUserById(id)
        existingUser.phoneNo = userDetails.phoneNo
        existingUser.name = userDetails.name
        existingUser.email = userDetails.email
        existingUser.address = userDetails.address
        existingUser.setLocation(userDetails.getLocation())
        existingUser.password = userDetails.password
        return userDetailsRepository.save(existingUser)
    }

    fun deleteUser(id: Long?) {
        userDetailsRepository.deleteById(id)
    }
}