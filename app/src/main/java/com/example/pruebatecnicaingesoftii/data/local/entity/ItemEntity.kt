package com.example.pruebatecnicaingesoftii.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pruebatecnicaingesoftii.domain.model.Item

/**
 * Entidad de Room que representa la tabla en la base de datos local.
 * Capa Data.
 */
@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val description: String
) {
    fun toItem(): Item {
        return Item(id, title, description)
    }
}
