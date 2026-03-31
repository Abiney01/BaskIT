package com.app.app.Services

import com.app.app.Models.Location
import com.app.app.Repository.LocationRepository
import org.springframework.stereotype.Service

@Service
class LocationService(
    private val locationRepository: LocationRepository
) {

    fun getAllLocations(): List<Location> {
        return locationRepository.findAll()
    }

    fun getLocationById(id: Long): Location {
        return locationRepository.findById(id)
            .orElseThrow { RuntimeException("Location not found with ID: $id") }
    }

    fun createLocation(location: Location): Location {
        return locationRepository.save(location)
    }

    fun updateLocation(id: Long, location: Location): Location {
        val existingLocation = getLocationById(id)
        existingLocation.locationName = location.locationName
        return locationRepository.save(existingLocation)
    }

    fun deleteLocation(id: Long) {
        locationRepository.deleteById(id)
    }
}