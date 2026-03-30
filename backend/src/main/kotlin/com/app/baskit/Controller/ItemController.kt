package com.app.baskit.Controller

import com.app.baskit.dto.ItemDTO
import com.app.baskit.Models.Item
import com.app.baskit.Service.ItemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/items")
class ItemController {
    @Autowired
    private val itemService: ItemService? = null

    @GetMapping
    @CrossOrigin(origins = ["http://localhost:3000"])
    fun getItems(
        @RequestParam("location_id") locationId: Long?,
        @RequestParam(value = "category_id", required = false) categoryId: Long?
    ): ResponseEntity<MutableList<ItemDTO?>?> {
        val items: MutableList<ItemDTO?>?
        if (categoryId != null) {
            items = itemService.getItemsByLocationAndCategory(locationId, categoryId)
        } else {
            items = itemService.getItemsByLocation(locationId)
        }
        return ResponseEntity.ok<MutableList<ItemDTO?>?>(items)
    }

    @GetMapping("/{id}")
    fun getItemById(@PathVariable id: Long?): ResponseEntity<Item?> {
        val item: Item? = itemService.getItemById(id)
        return ResponseEntity.ok<Item?>(item)
    }

    @PostMapping
    fun createItem(@RequestBody item: Item?): ResponseEntity<Item?> {
        val newItem: Item? = itemService.createItem(item)
        return ResponseEntity.ok<Item?>(newItem)
    }

    @PutMapping("/{id}")
    fun updateItem(@PathVariable id: Long?, @RequestBody item: Item?): ResponseEntity<Item?> {
        val updatedItem: Item? = itemService.updateItem(id, item)
        return ResponseEntity.ok<Item?>(updatedItem)
    }

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: Long?): ResponseEntity<Void?> {
        itemService.deleteItem(id)
        return ResponseEntity.noContent().build<Void?>()
    }
}