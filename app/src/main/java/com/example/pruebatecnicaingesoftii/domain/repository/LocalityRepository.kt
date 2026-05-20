package com.example.pruebatecnicaingesoftii.domain.repository

import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import com.example.pruebatecnicaingesoftii.data.local.entity.LocalityEntity
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz para el repositorio de localidades.
 * Define la abstracción para la sincronización y obtención de datos.
 */
interface LocalityRepository {
    /**
     * Sincroniza las localidades desde la red y las guarda en la base de datos local.
     */
    suspend fun syncLocalities(): NetworkResult<Unit>

    /**
     * Obtiene las localidades almacenadas localmente.
     */
    fun getLocalLocalities(): Flow<List<LocalityEntity>>

    /**
     * Sincroniza el esquema de tablas y lo guarda localmente.
     */
    suspend fun syncSchema(): NetworkResult<Unit>
}
