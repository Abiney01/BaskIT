package com.app.baskit.Controller

import com.app.baskit.Models.StoreDetails
import com.app.baskit.Service.StoreDetailsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/stores")
@CrossOrigin(origins = ["http://localhost:3000"])
class StoreDetailsController(
    private val storeDetailsService: StoreDetailsService   // ✅ constructor injection
) {

    @GetMapping
    fun getAllStores(): List<StoreDetails> {
        return storeDetailsService.getAllStores()
    }

    @GetMapping("/{id}")
    fun getStoreById(@PathVariable id: Long): ResponseEntity<StoreDetails> {
        val store = storeDetailsService.getStoreById(id)
        return ResponseEntity.ok(store)
    }

    @PostMapping
    fun createStore(@RequestBody storeDetails: StoreDetails): ResponseEntity<StoreDetails> {
        val newStore = storeDetailsService.createStore(storeDetails)
        return ResponseEntity.ok(newStore)
    }

    @PutMapping("/{id}")
    fun updateStore(
        @PathVariable id: Long,
        @RequestBody storeDetails: StoreDetails
    ): ResponseEntity<StoreDetails> {
        val updatedStore = storeDetailsService.updateStore(id, storeDetails)
        return ResponseEntity.ok(updatedStore)
    }

    @DeleteMapping("/{id}")
    fun deleteStore(@PathVariable id: Long): ResponseEntity<Void> {
        storeDetailsService.deleteStore(id)
        return ResponseEntity.noContent().build()
    }
}