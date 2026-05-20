package com.example.pruebatecnicaingesoftii.presentation.state

import com.example.pruebatecnicaingesoftii.domain.model.Item

/**
 * Representa el estado de la UI para la lista de ítems.
 */
data class ItemListState(
    val isLoading: Boolean = false,
    val items: List<Item> = emptyList(),
    val error: String = ""
)
