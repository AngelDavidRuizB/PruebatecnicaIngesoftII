package com.example.pruebatecnicaingesoftii.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de Room para persistir las localidades obtenidas de la red.
 */
@Entity(tableName = "localities")
data class LocalityEntity(
    @PrimaryKey(autoGenerate = true) val localId: Int = 0,
    val name: String,
    val code: String? = null
)
