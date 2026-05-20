package com.example.pruebatecnicaingesoftii.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa una tabla obtenida del esquema del sincronizador.
 */
@Entity(tableName = "schema_tables")
data class SchemaTableEntity(
    @PrimaryKey val tableName: String,
    val description: String? = null
)
