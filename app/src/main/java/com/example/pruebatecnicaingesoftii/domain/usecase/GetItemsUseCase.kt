package com.example.pruebatecnicaingesoftii.domain.usecase

import com.example.pruebatecnicaingesoftii.domain.model.Item
import com.example.pruebatecnicaingesoftii.domain.repository.ItemRepository
import com.example.pruebatecnicaingesoftii.core.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para obtener la lista de ítems.
 * Representa una acción específica del negocio (SOLID: SRP).
 */
class GetItemsUseCase @Inject constructor(
    private val repository: ItemRepository
) {
    operator fun invoke(): Flow<Resource<List<Item>>> {
        return repository.getItems()
    }
}
