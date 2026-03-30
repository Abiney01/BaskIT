package com.app.baskit.Controller

import com.app.baskit.Models.ListOfItems
import com.app.baskit.Service.ListOfItemsService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/list-of-items")
@CrossOrigin(origins = ["http://localhost:3000"])
class ListOfItemsController(
    private val listOfItemsService: ListOfItemsService
) {

    @GetMapping
    fun getAllItems(): List<ListOfItems> =
        listOfItemsService.getAllItems()

    @GetMapping("/{id}")
    fun getItemById(@PathVariable id: Long): ResponseEntity<ListOfItems> =
        ResponseEntity.ok(listOfItemsService.getItemById(id))

    @PostMapping
    fun addItemToCart(@RequestBody body: Map<String, Any>): ResponseEntity<ListOfItems> {

        val cartId = (body["cartId"] as Number).toLong()
        val itemId = (body["itemId"] as Number).toLong()
        val quantity = body["quantity"] as Int

        val item = listOfItemsService.addItemToCart(cartId, itemId, quantity)

        return ResponseEntity.status(201).body(item)
    }

    @PutMapping("/{id}")
    fun updateItem(
        @PathVariable id: Long,
        @RequestBody body: Map<String, Any>
    ): ResponseEntity<ListOfItems> {

        val cartId = (body["cartId"] as Number).toLong()
        val itemId = (body["itemId"] as Number).toLong()
        val quantity = body["quantity"] as Int

        val updated = listOfItemsService.updateItem(id, cartId, itemId, quantity)

        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: Long): ResponseEntity<Void> {
        listOfItemsService.deleteItem(id)
        return ResponseEntity.noContent().build()
    }
}