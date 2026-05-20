package com.example.pruebatecnicaingesoftii.data.repository

import com.example.pruebatecnicaingesoftii.core.util.Resource
import com.example.pruebatecnicaingesoftii.domain.model.Item
import com.example.pruebatecnicaingesoftii.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementación del repositorio. Capa Data.
 * Aquí se manejaría la lógica de decidir si los datos vienen de local (Room) o remoto (Retrofit).
 */
class ItemRepositoryImpl @Inject constructor() : ItemRepository {

    override fun getItems(): Flow<Resource<List<Item>>> = flow {
        emit(Resource.Loading())
        try {
            // Simulación de datos. En una app real, aquí llamarías a Room o Retrofit.
            val mockItems = listOf(
                Item(1, "SOLID Principle", "Single Responsibility"),
                Item(2, "MVVM", "Model-View-ViewModel"),
                Item(3, "Clean Architecture", "Layers separation")
            )
            emit(Resource.Success(mockItems))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error desconocido"))
        }
    }

    override suspend fun getItemById(id: Int): Item? {
        return null
    }
}
