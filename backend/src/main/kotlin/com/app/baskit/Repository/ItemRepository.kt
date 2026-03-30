package com.app.baskit.Repository

import com.app.baskit.Models.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long> {

    @Query(
        "SELECT i, s.stockAvailability, c.categoryName FROM Item i " +
                "JOIN StorageDetails s ON i.itemId = s.item.itemId " +
                "JOIN StoreDetails st ON s.store.storeId = st.storeId " +
                "JOIN Category c ON i.category.categoryId = c.categoryId " +
                "WHERE st.location.locationId = :locationId"
    )
    fun findItemsByLocationId(@Param("locationId") locationId: Long): List<Array<Any>>

    @Query(
        "SELECT i, s.stockAvailability, c.categoryName FROM Item i " +
                "JOIN StorageDetails s ON i.itemId = s.item.itemId " +
                "JOIN StoreDetails st ON s.store.storeId = st.storeId " +
                "JOIN Category c ON i.category.categoryId = c.categoryId " +
                "WHERE st.location.locationId = :locationId AND i.category.categoryId = :categoryId"
    )
    fun findItemsByLocationAndCategory(
        @Param("locationId") locationId: Long,
        @Param("categoryId") categoryId: Long
    ): List<Array<Any>>
}