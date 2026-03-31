package com.app.app.Controller

import com.app.app.Models.Category
import com.app.app.Services.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["http://localhost:3000"])
@RestController
@RequestMapping("/api/categories")
class CategoryController(
    private val categoryService: CategoryService   // ✅ constructor injection
) {

    @GetMapping
    fun getAllCategories(): List<Category> =
        categoryService.getAllCategories()

    @GetMapping("/{id}")
    fun getCategoryById(@PathVariable id: Long): ResponseEntity<Category> {
        return ResponseEntity.ok(categoryService.getCategoryById(id))
    }

    @PostMapping
    fun createCategory(@RequestBody category: Category): ResponseEntity<Category> {
        return ResponseEntity.ok(categoryService.createCategory(category))
    }

    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable id: Long,
        @RequestBody category: Category
    ): ResponseEntity<Category> {
        return ResponseEntity.ok(categoryService.updateCategory(id, category))
    }

    @DeleteMapping("/{id}")
    fun deleteCategory(@PathVariable id: Long): ResponseEntity<Void> {
        categoryService.deleteCategory(id)
        return ResponseEntity.noContent().build()
    }
}