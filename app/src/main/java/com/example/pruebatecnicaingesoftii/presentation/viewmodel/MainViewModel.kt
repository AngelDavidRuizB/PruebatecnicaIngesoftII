package com.example.pruebatecnicaingesoftii.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import com.example.pruebatecnicaingesoftii.domain.usecase.AuthAndSyncUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel principal para manejar el estado de inicio de la aplicación.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val authAndSyncUseCase: AuthAndSyncUseCase
) : ViewModel() {

    private val _initStatus = MutableStateFlow<String>("Esperando...")
    val initStatus: StateFlow<String> = _initStatus

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun startInitialSetup() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = authAndSyncUseCase()
            _isLoading.value = false
            
            when (result) {
                is NetworkResult.Success -> _initStatus.value = result.data
                is NetworkResult.Error -> _initStatus.value = "Error ${result.code}: ${result.message}"
                is NetworkResult.Exception -> _initStatus.value = "Excepción: ${result.e.localizedMessage}"
            }
        }
    }
}
