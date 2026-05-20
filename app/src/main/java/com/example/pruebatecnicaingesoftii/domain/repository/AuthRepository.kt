package com.example.pruebatecnicaingesoftii.domain.repository

import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import com.example.pruebatecnicaingesoftii.data.local.entity.UserEntity

/**
 * Interfaz para el repositorio de autenticación.
 */
interface AuthRepository {
    /**
     * Realiza la autenticación y guarda la sesión localmente si es exitosa.
     */
    suspend fun login(): NetworkResult<UserEntity>

    /**
     * Obtiene la sesión actual almacenada.
     */
    suspend fun getSavedSession(): UserEntity?
}
