package com.app.baskit.Service

import com.app.baskit.dto.ItemDTO
import com.app.baskit.Models.Item
import com.app.baskit.Repository.ItemRepository
import org.springframework.stereotype.Service

@Service
class ItemService(
    private val itemRepository: ItemRepository
) {

    fun getItemById(id: Long): Item {
        return itemRepository.findById(id)
            .orElseThrow { RuntimeException("Item not found with ID: $id") }
    }

    fun createItem(item: Item): Item {
        return itemRepository.save(item)
    }

    fun updateItem(id: Long, item: Item): Item {
        val existingItem = getItemById(id)

        existingItem.itemName = item.itemName
        existingItem.price = item.price
        existingItem.category = item.category
        existingItem.offer = item.offer

        return itemRepository.save(existingItem)
    }

    fun deleteItem(id: Long) {
        itemRepository.deleteById(id)
    }

    fun getItemsByLocation(locationId: Long): List<ItemDTO> {
        val results = itemRepository.findItemsByLocationId(locationId)
        return mapToDTO(results)
    }

    fun getItemsByLocationAndCategory(locationId: Long, categoryId: Long): List<ItemDTO> {
        val results = itemRepository.findItemsByLocationAndCategory(locationId, categoryId)
        return mapToDTO(results)
    }

    private fun mapToDTO(results: List<Array<Any>>): List<ItemDTO> {
        return results.map {
            val item = it[0] as Item
            val stockAvailability = it[1] as Int?
            val categoryName = it[2] as String?

            ItemDTO(
                item.itemId,
                item.itemName,
                item.price,
                stockAvailability,
                categoryName
            )
        }
    }
}