package com.app.app.Controller

import com.app.app.Models.Location
import com.app.app.Services.LocationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/locations")
class LocationController(
    private val locationService: LocationService
) {

    @GetMapping
    fun getAllLocations(): List<Location> {
        return locationService.getAllLocations()
    }

    @GetMapping("/{id}")
    fun getLocationById(@PathVariable id: Long): ResponseEntity<Location> {
        val location = locationService.getLocationById(id)
        return ResponseEntity.ok(location)
    }

    @PostMapping
    fun createLocation(@RequestBody location: Location): ResponseEntity<Location> {
        val newLocation = locationService.createLocation(location)
        return ResponseEntity.ok(newLocation)
    }

    @PutMapping("/{id}")
    fun updateLocation(
        @PathVariable id: Long,
        @RequestBody location: Location
    ): ResponseEntity<Location> {
        val updatedLocation = locationService.updateLocation(id, location)
        return ResponseEntity.ok(updatedLocation)
    }

    @DeleteMapping("/{id}")
    fun deleteLocation(@PathVariable id: Long): ResponseEntity<Void> {
        locationService.deleteLocation(id)
        return ResponseEntity.noContent().build()
    }
}