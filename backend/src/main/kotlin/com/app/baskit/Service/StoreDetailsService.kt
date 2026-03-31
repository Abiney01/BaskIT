package com.app.baskit.Service

import com.app.baskit.Models.StoreDetails
import com.app.baskit.Repository.StoreDetailsRepository
import org.springframework.stereotype.Service

@Service
class StoreDetailsService(
    private val storeDetailsRepository: StoreDetailsRepository   // ✅ constructor injection
) {

    fun getAllStores(): List<StoreDetails> {
        return storeDetailsRepository.findAll()
    }

    fun getStoreById(id: Long): StoreDetails {
        return storeDetailsRepository.findById(id)
            .orElseThrow { RuntimeException("Store not found with ID: $id") }
    }

    fun createStore(storeDetails: StoreDetails): StoreDetails {
        return storeDetailsRepository.save(storeDetails)
    }

    fun updateStore(id: Long, storeDetails: StoreDetails): StoreDetails {
        val existing = getStoreById(id)

        existing.storeName = storeDetails.storeName
        existing.storeContactInfo = storeDetails.storeContactInfo
        existing.location = storeDetails.location

        return storeDetailsRepository.save(existing)
    }

    fun deleteStore(id: Long) {
        storeDetailsRepository.deleteById(id)
    }
}