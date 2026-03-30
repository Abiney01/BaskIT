package com.app.baskit.Controller

import com.app.baskit.dto.ItemDTO
import com.app.baskit.Models.Item
import com.app.baskit.Service.ItemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = ["http://localhost:3000"])
class ItemController(
    private val itemService: ItemService
) {

    @GetMapping
    fun getItems(
        @RequestParam("location_id") locationId: Long,
        @RequestParam(value = "category_id", required = false) categoryId: Long?
    ): ResponseEntity<List<ItemDTO>> {

        val items = if (categoryId != null) {
            itemService.getItemsByLocationAndCategory(locationId, categoryId)
        } else {
            itemService.getItemsByLocation(locationId)
        }

        return ResponseEntity.ok(items)
    }

    @GetMapping("/{id}")
    fun getItemById(@PathVariable id: Long): ResponseEntity<Item> {
        return ResponseEntity.ok(itemService.getItemById(id))
    }

    @PostMapping
    fun createItem(@RequestBody item: Item): ResponseEntity<Item> {
        return ResponseEntity.ok(itemService.createItem(item))
    }

    @PutMapping("/{id}")
    fun updateItem(@PathVariable id: Long, @RequestBody item: Item): ResponseEntity<Item> {
        return ResponseEntity.ok(itemService.updateItem(id, item))
    }

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: Long): ResponseEntity<Void> {
        itemService.deleteItem(id)
        return ResponseEntity.noContent().build()
    }
}