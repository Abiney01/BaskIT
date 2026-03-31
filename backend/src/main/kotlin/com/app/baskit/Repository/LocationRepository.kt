package com.app.app.Repository

import com.app.app.Models.Location
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LocationRepository : JpaRepository<Location, Long>