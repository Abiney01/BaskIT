package com.app.app.Services

import com.app.app.Models.DeliveryAgent
import com.app.app.Repository.DeliveryAgentRepository
import org.springframework.stereotype.Service

@Service
class DeliveryAgentService(
    private val deliveryAgentRepository: DeliveryAgentRepository
) {

    fun getAllDeliveryAgents(): List<DeliveryAgent> {
        return deliveryAgentRepository.findAll()
    }

    fun getDeliveryAgentById(id: Long): DeliveryAgent {
        return deliveryAgentRepository.findById(id)
            .orElseThrow { RuntimeException("DeliveryAgent not found with ID: $id") }
    }

    fun createDeliveryAgent(deliveryAgent: DeliveryAgent): DeliveryAgent {
        return deliveryAgentRepository.save(deliveryAgent)
    }

    fun updateDeliveryAgent(id: Long, deliveryAgent: DeliveryAgent): DeliveryAgent {
        val existingAgent = getDeliveryAgentById(id)
        existingAgent.agentName = deliveryAgent.agentName
        existingAgent.phoneNo = deliveryAgent.phoneNo
        existingAgent.email = deliveryAgent.email
        existingAgent.location = deliveryAgent.location
        existingAgent.vehicleDetails = deliveryAgent.vehicleDetails
        existingAgent.availabilityStatus = deliveryAgent.availabilityStatus
        return deliveryAgentRepository.save(existingAgent)
    }

    fun deleteDeliveryAgent(id: Long) {
        deliveryAgentRepository.deleteById(id)
    }
}