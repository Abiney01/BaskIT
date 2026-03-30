package com.app.baskit.Service

import com.app.baskit.Models.ListOfItems
import com.app.baskit.Repository.CartRepository
import com.app.baskit.Repository.ItemRepository
import com.app.baskit.Repository.ListOfItemsRepository
import org.springframework.stereotype.Service

@Service
class ListOfItemsService(
    private val listOfItemsRepository: ListOfItemsRepository,
    private val cartRepository: CartRepository,
    private val itemRepository: ItemRepository
) {

    fun getAllItems(): List<ListOfItems> =
        listOfItemsRepository.findAll()

    fun getItemById(id: Long): ListOfItems =
        listOfItemsRepository.findById(id)
            .orElseThrow { RuntimeException("Item not found") }

    fun getItemsByCartId(cartId: Long): List<ListOfItems> =
        listOfItemsRepository.findByCart_CartId(cartId)

    fun addItemToCart(cartId: Long, itemId: Long, quantity: Int): ListOfItems {

        val cart = cartRepository.findById(cartId)
            .orElseThrow { RuntimeException("Cart not found") }

        val item = itemRepository.findById(itemId)
            .orElseThrow { RuntimeException("Item not found") }

        val existingItem =
            listOfItemsRepository.findByCart_CartIdAndItem_ItemId(cartId, itemId)

        if (existingItem != null) {
            existingItem.quantity += quantity
            return listOfItemsRepository.save(existingItem)
        }

        val newItem = ListOfItems().apply {
            this.cart = cart
            this.item = item
            this.quantity = quantity
        }

        return listOfItemsRepository.save(newItem)
    }

    fun updateItem(id: Long, cartId: Long, itemId: Long, quantity: Int): ListOfItems {

        val existingItem = getItemById(id)

        val cart = cartRepository.findById(cartId)
            .orElseThrow { RuntimeException("Cart not found") }

        val item = itemRepository.findById(itemId)
            .orElseThrow { RuntimeException("Item not found") }

        existingItem.cart = cart
        existingItem.item = item
        existingItem.quantity = quantity

        return listOfItemsRepository.save(existingItem)
    }

    fun deleteItem(id: Long) {
        listOfItemsRepository.deleteById(id)
    }
}