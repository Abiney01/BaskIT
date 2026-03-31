package com.app.baskit.Repository

import com.app.baskit.Models.StoreDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface StoreDetailsRepository : JpaRepository<StoreDetails, Long?> {
    @Query("SELECT st.storeId FROM StoreDetails st WHERE st.location.locationId = :locationId")
    fun findStoreIdsByLocationId(@Param("locationId") locationId: Long?): MutableList<Long?>?
}