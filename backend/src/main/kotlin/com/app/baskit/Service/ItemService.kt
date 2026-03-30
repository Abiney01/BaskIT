package com.app.baskit.Service

import com.app.baskit.dto.ItemDTO
import com.app.baskit.Models.Item
import com.app.baskit.Repository.ItemRepository
import com.app.baskit.Repository.LocationRepository
import com.app.baskit.Repository.StorageDetailsRepository
import com.app.baskit.Repository.StoreDetailsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ItemService {
    @Autowired
    private val itemRepository: ItemRepository? = null

    @Autowired
    private val storageDetailsRepository: StorageDetailsRepository? = null

    @Autowired
    private val storeRepository: StoreDetailsRepository? = null

    @Autowired
    private val locationRepository: LocationRepository? = null

    val allItems: MutableList<Item>
        get() = itemRepository.findAll()

    fun getItemById(id: Long?): Item {
        return itemRepository.findById(id)
            .orElseThrow({ RuntimeException("Item not found with ID: " + id) })
    }

    fun createItem(item: Item?): Item {
        return itemRepository.save(item)
    }

    fun updateItem(id: Long?, item: Item): Item {
        val existingItem: Item = getItemById(id)
        existingItem.setItemName(item.getItemName())
        existingItem.setPrice(item.getPrice())
        existingItem.setCategory(item.getCategory())
        existingItem.setOffer(item.getOffer())
        return itemRepository.save(existingItem)
    }

    fun deleteItem(id: Long?) {
        itemRepository.deleteById(id)
    }

    fun getItemsByLocation(locationId: Long?): MutableList<ItemDTO?> {
        val results: MutableList<Array<Any?>?> = itemRepository.findItemsByLocationId(locationId)
        return mapToDTO(results)
    }

    fun getItemsByLocationAndCategory(locationId: Long?, categoryId: Long?): MutableList<ItemDTO?> {
        val results: MutableList<Array<Any?>?> = itemRepository.findItemsByLocationAndCategory(locationId, categoryId)
        return mapToDTO(results)
    }

    private fun mapToDTO(results: MutableList<Array<Any?>?>): MutableList<ItemDTO?> {
        val items: MutableList<ItemDTO?> = ArrayList<ItemDTO?>()
        for (result in results) {
            val item: Item = result!![0] as Item
            val stockAvailability = result[1] as Int? // Extract stockAvailability
            val categoryName = result[2] as String?
            val dto: ItemDTO = ItemDTO(
                item.getItemId(),
                item.getItemName(),
                item.getPrice(),
                stockAvailability,
                categoryName
            )
            items.add(dto)
        }
        return items
    }
}