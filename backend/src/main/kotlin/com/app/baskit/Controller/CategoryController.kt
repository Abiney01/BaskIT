package com.app.app.Controller

import com.app.app.Models.Category
import com.app.app.Services.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/categories")
class CategoryController {
    @Autowired
    private val categoryService: CategoryService? = null

    @get:GetMapping
    val allCategories: MutableList<Category>
        get() = categoryService.getAllCategories()

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long?): ResponseEntity<Category?> {
        val category: Category? = categoryService.getCategoryById(id)
        return ResponseEntity.ok(category)
    }

    @PostMapping
    fun createCategory(@RequestBody category: Category?): ResponseEntity<Category?> {
        val newCategory: Category? = categoryService.createCategory(category)
        return ResponseEntity.ok(newCategory)
    }

    @PutMapping("/{id}")
    fun updateCategory(@PathVariable id: Long?, @RequestBody category: Category?): ResponseEntity<Category?> {
        val updatedCategory: Category? = categoryService.updateCategory(id, category)
        return ResponseEntity.ok(updatedCategory)
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long?): ResponseEntity<Void?> {
        categoryService.deleteCategory(id)
        return ResponseEntity.noContent().build()
    }
}