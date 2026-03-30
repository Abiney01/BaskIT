package com.app.baskit.Repository

import com.app.baskit.Models.ListOfItems
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ListOfItemsRepository : JpaRepository<ListOfItems, Long> {

    fun findByCart_CartId(cartId: Long): List<ListOfItems>

    fun findByCart_CartIdAndItem_ItemId(cartId: Long, itemId: Long): ListOfItems?
}