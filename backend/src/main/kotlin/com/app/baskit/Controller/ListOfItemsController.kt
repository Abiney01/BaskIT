package com.app.baskit.Controller

import com.app.baskit.Models.Cart
import com.app.baskit.Models.Item
import com.app.baskit.Models.ListOfItems
import com.app.baskit.Service.CartService
import com.app.baskit.Service.ItemService
import com.app.baskit.Service.ListOfItemsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.Map

@RestController
@RequestMapping("/api/list-of-items")
class ListOfItemsController {
    @Autowired
    private val listOfItemsService: ListOfItemsService? = null

    @Autowired
    private val cartService: CartService? = null

    @Autowired
    private val itemService: ItemService? = null

    @get:GetMapping
    val allItems: MutableList<ListOfItems>
        // Get all items in a cart
        get() = listOfItemsService.getAllItems()

    // Get an item by its ID
    @GetMapping("/{id}")
    fun getItemById(@PathVariable id: Long?): ResponseEntity<ListOfItems?> {
        val listOfItems: ListOfItems? = listOfItemsService.getItemById(id)
        return ResponseEntity.ok<ListOfItems?>(listOfItems)
    }

    // Add an item to the cart
    @PostMapping
    fun addItemToCart(@RequestBody requestBody: MutableMap<String?, Any?>): ResponseEntity<*> {
        try {
            // Validate request body
            if (!requestBody.containsKey("cartId") || !requestBody.containsKey("itemId") || !requestBody.containsKey("quantity")) {
                return ResponseEntity.badRequest()
                    .body<MutableMap<String?, String?>?>(Map.of<String?, String?>("error", "Missing required fields"))
            }

            val cartId = (requestBody.get("cartId") as Number).toLong()
            val itemId = (requestBody.get("itemId") as Number).toLong()
            val quantity = requestBody.get("quantity") as Int?

            if (cartId == null || itemId == null || quantity == null || quantity <= 0) {
                return ResponseEntity.badRequest()
                    .body<MutableMap<String?, String?>?>(Map.of<String?, String?>("error", "Invalid request data"))
            }

            // Fetch cart and item
            val cart: Cart? = cartService.getCartById(cartId)

            val item: Item? = itemService.getItemById(itemId)

            // Check if the item already exists in the cart
            val existingItem: Optional<ListOfItems> = listOfItemsService.findByCart_CartIdAndItem_ItemId(cartId, itemId)
            if (existingItem.isPresent()) {
                // If item already exists, update quantity
                val existing: ListOfItems = existingItem.get()
                existing.setQuantity(existing.getQuantity() + quantity) // Increase quantity
                return ResponseEntity.ok<T?>(listOfItemsService.save(existing))
            }

            // If item doesn't exist in the cart, create a new entry
            val newItem: ListOfItems = ListOfItems()
            newItem.setCart(cart)
            newItem.setItem(item)
            newItem.setQuantity(quantity)

            return ResponseEntity.status(201).body<T?>(listOfItemsService.save(newItem))
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.status(500).body<MutableMap<String?, String?>?>(
                Map.of<String?, String?>(
                    "error",
                    "Error adding item to cart",
                    "message",
                    e.message
                )
            )
        }
    }

    // Update quantity of an item in the cart
    @PutMapping("/{id}")
    fun updateItem(@PathVariable id: Long?, @RequestBody requestBody: MutableMap<String?, Any?>): ResponseEntity<*> {
        try {
            if (!requestBody.containsKey("cartId") || !requestBody.containsKey("itemId") || !requestBody.containsKey("quantity")) {
                return ResponseEntity.badRequest()
                    .body<MutableMap<String?, String?>?>(Map.of<String?, String?>("error", "Missing required fields"))
            }

            val cartId = (requestBody.get("cartId") as Number).toLong()
            val itemId = (requestBody.get("itemId") as Number).toLong()
            val quantity = requestBody.get("quantity") as Int?

            if (cartId == null || itemId == null || quantity == null || quantity <= 0) {
                return ResponseEntity.badRequest()
                    .body<MutableMap<String?, String?>?>(Map.of<String?, String?>("error", "Invalid request data"))
            }

            val existingItem: ListOfItems = listOfItemsService.getItemById(id)
            val cart: Cart? = cartService.getCartById(cartId)


            val item: Item? = itemService.getItemById(itemId)

            existingItem.setCart(cart)
            existingItem.setItem(item)
            existingItem.setQuantity(quantity)

            return ResponseEntity.ok<T?>(listOfItemsService.save(existingItem))
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.status(500).body<MutableMap<String?, String?>?>(
                Map.of<String?, String?>(
                    "error",
                    "Error updating cart item",
                    "message",
                    e.message
                )
            )
        }
    }

    // Delete an item from the cart
    @DeleteMapping("/{id}")
    fun deleteItem(@PathVariable id: Long?): ResponseEntity<Void?> {
        listOfItemsService.deleteItem(id)
        return ResponseEntity.noContent().build<Void?>()
    }
}