package com.app.baskit.Service

import com.app.baskit.Models.StorageDetails
import com.app.baskit.Repository.StorageDetailsRepository
import org.springframework.stereotype.Service

@Service
class StorageDetailsService(
    private val storageDetailsRepository: StorageDetailsRepository   // ✅ constructor injection
) {

    fun getAllStorageDetails(): List<StorageDetails> {
        return storageDetailsRepository.findAll()
    }

    fun getStorageDetailsById(id: Long): StorageDetails {
        return storageDetailsRepository.findById(id)
            .orElseThrow { RuntimeException("StorageDetails not found with ID: $id") }
    }

    fun createStorageDetails(storageDetails: StorageDetails): StorageDetails {
        return storageDetailsRepository.save(storageDetails)
    }

    fun updateStorageDetails(id: Long, storageDetails: StorageDetails): StorageDetails {
        val existing = getStorageDetailsById(id)

        existing.store = storageDetails.store
        existing.item = storageDetails.item
        existing.stockAvailability = storageDetails.stockAvailability

        return storageDetailsRepository.save(existing)
    }

    fun deleteStorageDetails(id: Long) {
        storageDetailsRepository.deleteById(id)
    }
}