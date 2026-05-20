package com.example.pruebatecnicaingesoftii.domain.repository

import com.example.pruebatecnicaingesoftii.domain.model.Item
import kotlinx.coroutines.flow.Flow
import com.example.pruebatecnicaingesoftii.core.util.Resource

/**
 * Interfaz del repositorio que define las operaciones permitidas.
 * Se encuentra en la capa de Domain para aplicar Inversión de Dependencias (SOLID: DIP).
 */
interface ItemRepository {
    fun getItems(): Flow<Resource<List<Item>>>
    suspend fun getItemById(id: Int): Item?
}
