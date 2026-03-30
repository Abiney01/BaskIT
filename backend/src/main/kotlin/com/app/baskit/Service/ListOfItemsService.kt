package com.app.baskit.Service

import com.app.baskit.Models.Cart
import com.app.baskit.Models.Item
import com.app.baskit.Models.ListOfItems
import com.app.baskit.Repository.CartRepository
import com.app.baskit.Repository.ItemRepository
import com.app.baskit.Repository.ListOfItemsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ListOfItemsService {
    @Autowired
    private val listOfItemsRepository: ListOfItemsRepository? = null

    @Autowired
    private val cartRepository: CartRepository? = null

    @Autowired
    private val itemRepository: ItemRepository? = null

    val allItems: MutableList<ListOfItems>
        get() = listOfItemsRepository.findAll()

    fun getItemById(id: Long?): ListOfItems {
        return listOfItemsRepository.findById(id)
            .orElseThrow({ RuntimeException("ListOfItems not found with ID: " + id) })
    }

    fun getItemsByCartId(cartId: Long?): MutableList<ListOfItems?> {
        return listOfItemsRepository.findByCart_CartId(cartId)
    }

    fun findByCart_CartIdAndItem_ItemId(cartId: Long?, itemId: Long?): Optional<ListOfItems?> {
        return listOfItemsRepository.findByCart_CartIdAndItem_ItemId(cartId, itemId)
    }

    fun createItem(listOfItems: ListOfItems): ListOfItems {
        val existingItem: Optional<ListOfItems> = listOfItemsRepository.findByCart_CartIdAndItem_ItemId(
            listOfItems.getCart().getCartId(),
            listOfItems.getItem().getItemId()
        )

        if (existingItem.isPresent()) {
            // If item already exists in cart, update quantity
            val existing: ListOfItems = existingItem.get()
            existing.setQuantity(existing.getQuantity() + listOfItems.getQuantity()) // Increase quantity
            return listOfItemsRepository.save(existing)
        }

        // Otherwise, save as a new entry
        return listOfItemsRepository.save(listOfItems)
    }


    fun updateItem(id: Long?, cartId: Long?, itemId: Long?, quantity: Int?): ListOfItems {
        val existingItem: ListOfItems = listOfItemsRepository.findById(id)
            .orElseThrow({ RuntimeException("Cart item not found") })

        val cart: Cart? = cartRepository.findById(cartId)
            .orElseThrow({ RuntimeException("Cart not found") })

        val item: Item? = itemRepository.findById(itemId)
            .orElseThrow({ RuntimeException("Item not found") })

        existingItem.setCart(cart)
        existingItem.setItem(item)
        existingItem.setQuantity(quantity)

        return listOfItemsRepository.save(existingItem)
    }

    fun addItemToCart(cartId: Long?, itemId: Long?, quantity: Int?): ListOfItems {
        val cart: Cart? = cartRepository.findById(cartId)
            .orElseThrow({ RuntimeException("Cart not found") })

        val item: Item? = itemRepository.findById(itemId)
            .orElseThrow({ RuntimeException("Item not found") })

        val existingItem: Optional<ListOfItems> = listOfItemsRepository.findByCart_CartIdAndItem_ItemId(cartId, itemId)

        if (existingItem.isPresent()) {
            val existing: ListOfItems = existingItem.get()
            existing.setQuantity(existing.getQuantity() + quantity)
            return listOfItemsRepository.save(existing)
        }

        val newItem: ListOfItems = ListOfItems()
        newItem.setCart(cart)
        newItem.setItem(item)
        newItem.setQuantity(quantity)

        return listOfItemsRepository.save(newItem)
    }

    fun save(listOfItems: ListOfItems?): ListOfItems {
        return listOfItemsRepository.save(listOfItems)
    }


    fun deleteItem(id: Long?) {
        listOfItemsRepository.deleteById(id)
    }
}