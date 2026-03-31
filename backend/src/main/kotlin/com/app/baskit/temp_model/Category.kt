package com.app.app.Models

import jakarta.persistence.*

@Entity
@Table(name = "category")
class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var categoryId: Long? = null

    var categoryName: String? = null
}