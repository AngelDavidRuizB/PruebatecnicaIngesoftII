package com.example.pruebatecnicaingesoftii.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad que representa la sesión del usuario autenticado.
 * SOLID: SRP - Solo contiene datos persistentes del usuario.
 */
@Entity(tableName = "user_session")
data class UserEntity(
    @PrimaryKey val identificacion: String,
    val usuario: String,
    val nombre: String
)
