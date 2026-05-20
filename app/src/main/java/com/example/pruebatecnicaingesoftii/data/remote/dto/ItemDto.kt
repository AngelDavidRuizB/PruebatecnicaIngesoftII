package com.example.pruebatecnicaingesoftii.data.remote.dto

import com.example.pruebatecnicaingesoftii.data.local.entity.ItemEntity
import com.example.pruebatecnicaingesoftii.domain.model.Item
import com.google.gson.annotations.SerializedName

/**
 * Objeto de transferencia de datos (DTO) para la respuesta de la API.
 */
data class ItemDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
) {
    fun toItem(): Item {
        return Item(id, title, body)
    }

    fun toItemEntity(): ItemEntity {
        return ItemEntity(id, title, body)
    }
}
