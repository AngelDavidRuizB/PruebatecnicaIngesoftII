package com.example.pruebatecnicaingesoftii.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicaingesoftii.data.local.entity.SchemaTableEntity
import com.example.pruebatecnicaingesoftii.domain.usecase.GetLocalTablesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TablasState(
    val tables: List<SchemaTableEntity> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
class TablasViewModel @Inject constructor(
    private val getLocalTablesUseCase: GetLocalTablesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TablasState())
    val state: StateFlow<TablasState> = _state

    init {
        loadTables()
    }

    private fun loadTables() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val tables = getLocalTablesUseCase()
            _state.value = _state.value.copy(tables = tables, isLoading = false)
        }
    }
}
