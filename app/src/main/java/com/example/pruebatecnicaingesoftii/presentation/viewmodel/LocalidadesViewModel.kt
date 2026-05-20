package com.example.pruebatecnicaingesoftii.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import com.example.pruebatecnicaingesoftii.data.remote.dto.LocalityDto
import com.example.pruebatecnicaingesoftii.domain.usecase.GetLocalidadesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LocalidadesState(
    val localities: List<LocalityDto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class LocalidadesViewModel @Inject constructor(
    private val getLocalidadesUseCase: GetLocalidadesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LocalidadesState())
    val state: StateFlow<LocalidadesState> = _state

    init {
        loadLocalities()
    }

    fun loadLocalities() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)
            val result = getLocalidadesUseCase()
            
            when (result) {
                is NetworkResult.Success -> {
                    val list = (result.data as? List<*>)?.filterIsInstance<LocalityDto>() ?: emptyList()
                    _state.value = _state.value.copy(localities = list, isLoading = false)
                }
                is NetworkResult.Error -> {
                    _state.value = _state.value.copy(
                        errorMessage = "Error ${result.code}: ${result.message}",
                        isLoading = false
                    )
                }
                is NetworkResult.Exception -> {
                    _state.value = _state.value.copy(
                        errorMessage = "Excepción: ${result.e.localizedMessage}",
                        isLoading = false
                    )
                }
            }
        }
    }
}
