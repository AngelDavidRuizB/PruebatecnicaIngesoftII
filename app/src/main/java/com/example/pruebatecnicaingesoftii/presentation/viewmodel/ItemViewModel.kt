package com.example.pruebatecnicaingesoftii.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicaingesoftii.core.util.Resource
import com.example.pruebatecnicaingesoftii.domain.usecase.GetItemsUseCase
import com.example.pruebatecnicaingesoftii.presentation.state.ItemListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * ViewModel que maneja la lógica de la UI y se comunica con los UseCases.
 * Sigue el patrón MVVM.
 */
@HiltViewModel
class ItemViewModel @Inject constructor(
    private val getItemsUseCase: GetItemsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ItemListState())
    val state: StateFlow<ItemListState> = _state

    init {
        getItems()
    }

    private fun getItems() {
        getItemsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ItemListState(items = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = ItemListState(
                        error = result.message ?: "An unexpected error occurred"
                    )
                }
                is Resource.Loading -> {
                    _state.value = ItemListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}
