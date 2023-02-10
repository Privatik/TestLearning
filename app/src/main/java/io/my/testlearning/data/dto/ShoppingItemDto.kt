package io.my.testlearning.data.dto

import io.my.testlearning.ShopEntity

data class ShoppingItemDto(
    val id: Long,
    val name: String,
    val amount: Long,
    val price: Double,
    val imageUrl: String?
)

fun ShopEntity.asDto(): ShoppingItemDto{
    return ShoppingItemDto(
        id = id,
        name = name,
        amount = amount,
        price = price,
        imageUrl = imageUrl
    )
}
