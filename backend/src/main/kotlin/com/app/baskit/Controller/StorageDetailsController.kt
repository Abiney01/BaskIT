package com.app.baskit.Controller

import com.app.baskit.Models.StorageDetails
import com.app.baskit.Service.StorageDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/storage")
@CrossOrigin(origins = ["http://localhost:3000"])
class StorageDetailsController(
    private val storageDetailsService: StorageDetailsService   // ✅ constructor injection
) {

    @GetMapping
    fun getAllStorageDetails(): List<StorageDetails> {
        return storageDetailsService.getAllStorageDetails()
    }

    @GetMapping("/{id}")
    fun getStorageDetailsById(@PathVariable id: Long): ResponseEntity<StorageDetails> {
        val storageDetails = storageDetailsService.getStorageDetailsById(id)
        return ResponseEntity.ok(storageDetails)
    }

    @PostMapping
    fun createStorageDetails(@RequestBody storageDetails: StorageDetails): ResponseEntity<StorageDetails> {
        val newStorageDetails = storageDetailsService.createStorageDetails(storageDetails)
        return ResponseEntity.ok(newStorageDetails)
    }

    @PutMapping("/{id}")
    fun updateStorageDetails(
        @PathVariable id: Long,
        @RequestBody storageDetails: StorageDetails
    ): ResponseEntity<StorageDetails> {
        val updated = storageDetailsService.updateStorageDetails(id, storageDetails)
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun deleteStorageDetails(@PathVariable id: Long): ResponseEntity<Void> {
        storageDetailsService.deleteStorageDetails(id)
        return ResponseEntity.noContent().build()
    }
}