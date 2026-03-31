package com.app.app.Controller

import com.app.app.Models.DeliveryAgent
import com.app.app.Services.DeliveryAgentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/agents")
class DeliveryAgentController {

    @Autowired
    private lateinit var deliveryAgentService: DeliveryAgentService

    @GetMapping
    fun getAllDeliveryAgents(): List<DeliveryAgent> {
        return deliveryAgentService.getAllDeliveryAgents()
    }

    @GetMapping("/{id}")
    fun getDeliveryAgentById(@PathVariable id: Long): ResponseEntity<DeliveryAgent> {
        val agent = deliveryAgentService.getDeliveryAgentById(id)
        return ResponseEntity.ok(agent)
    }

    @PostMapping
    fun createDeliveryAgent(@RequestBody deliveryAgent: DeliveryAgent): ResponseEntity<DeliveryAgent> {
        val newAgent = deliveryAgentService.createDeliveryAgent(deliveryAgent)
        return ResponseEntity.ok(newAgent)
    }

    @PutMapping("/{id}")
    fun updateDeliveryAgent(
        @PathVariable id: Long,
        @RequestBody deliveryAgent: DeliveryAgent
    ): ResponseEntity<DeliveryAgent> {
        val updatedAgent = deliveryAgentService.updateDeliveryAgent(id, deliveryAgent)
        return ResponseEntity.ok(updatedAgent)
    }

    @DeleteMapping("/{id}")
    fun deleteDeliveryAgent(@PathVariable id: Long): ResponseEntity<Void> {
        deliveryAgentService.deleteDeliveryAgent(id)
        return ResponseEntity.noContent().build()
    }
}