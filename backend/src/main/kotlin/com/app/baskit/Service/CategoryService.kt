package com.app.app.Services

@Service
class CategoryService {
    @Autowired
    private val categoryRepository: CategoryRepository? = null

    val allCategories: MutableList<Category?>
        get() = categoryRepository.findAll()

    fun getCategoryById(id: Long?): Category {
        return categoryRepository.findById(id)
            .orElseThrow({ RuntimeException("Category not found with ID: " + id) })
    }

    fun createCategory(category: Category?): Category {
        return categoryRepository.save(category)
    }

    fun updateCategory(id: Long?, category: Category): Category {
        val existingCategory = getCategoryById(id)
        existingCategory.categoryName = category.categoryName
        return categoryRepository.save(existingCategory)
    }

    fun deleteCategory(id: Long?) {
        categoryRepository.deleteById(id)
    }
}