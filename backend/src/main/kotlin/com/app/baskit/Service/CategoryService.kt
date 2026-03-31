package com.app.app.Services

import com.app.app.Models.Category
import com.app.app.Repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository   // ✅ constructor injection
) {

    // ✅ THIS FIXES YOUR RED ERROR
    fun getAllCategories(): List<Category> {
        return categoryRepository.findAll()
    }

    fun getCategoryById(id: Long): Category {
        return categoryRepository.findById(id)
            .orElseThrow { RuntimeException("Category not found with ID: $id") }
    }

    fun createCategory(category: Category): Category {
        return categoryRepository.save(category)
    }

    fun updateCategory(id: Long, category: Category): Category {
        val existingCategory = getCategoryById(id)
        existingCategory.categoryName = category.categoryName
        return categoryRepository.save(existingCategory)
    }

    fun deleteCategory(id: Long) {
        categoryRepository.deleteById(id)
    }
}