package com.app.baskit.Models

import jakarta.persistence.*

@Entity
@Table(name = "delivery_agent")
data class DeliveryAgent(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_agent_id")
    val deliveryAgentId: Long? = null,

    var agentName: String? = null,
    var phoneNo: Long? = null,
    var email: String? = null,

    @ManyToOne
    @JoinColumn(name = "location_id")
    var location: Location? = null,

    var vehicleDetails: String? = null,
    var availabilityStatus: Boolean? = null

)