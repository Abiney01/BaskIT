package com.app.baskit.Repository

import com.app.baskit.Models.StorageDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StorageDetailsRepository : JpaRepository<StorageDetails, Long?>