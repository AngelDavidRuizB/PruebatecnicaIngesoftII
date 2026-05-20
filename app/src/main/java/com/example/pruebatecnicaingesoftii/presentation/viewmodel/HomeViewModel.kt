package com.example.pruebatecnicaingesoftii.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import com.example.pruebatecnicaingesoftii.data.local.entity.UserEntity
import com.example.pruebatecnicaingesoftii.domain.repository.AuthRepository
import com.example.pruebatecnicaingesoftii.domain.usecase.AuthenticateUserUseCase
import com.example.pruebatecnicaingesoftii.domain.usecase.CheckVersionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeState(
    val user: UserEntity? = null,
    val versionMessage: String? = null,
    val loginMessage: String? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val checkVersionUseCase: CheckVersionUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        checkVersion("1.0.0")
        loadUserOrLogin()
    }

    private fun loadUserOrLogin() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val savedUser = authRepository.getSavedSession()
            if (savedUser != null) {
                _state.value = _state.value.copy(user = savedUser, isLoading = false)
            } else {
                performLogin()
            }
        }
    }

    fun performLogin() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, loginMessage = null)
            val result = authenticateUserUseCase()
            when (result) {
                is NetworkResult.Success -> {
                    _state.value = _state.value.copy(
                        user = result.data,
                        loginMessage = null,
                        isLoading = false
                    )
                }
                is NetworkResult.Error -> {
                    _state.value = _state.value.copy(
                        loginMessage = "Alerta: Error en autenticacion (${result.code}) - ${result.message}",
                        isLoading = false
                    )
                }
                is NetworkResult.Exception -> {
                    _state.value = _state.value.copy(
                        loginMessage = "Excepcion: ${result.e.localizedMessage}",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun checkVersion(localVersion: String) {
        viewModelScope.launch {
            val result = checkVersionUseCase(localVersion)
            val message = when (result) {
                is CheckVersionUseCase.VersionStatus.UpdateRequired ->
                    "Nueva version disponible: ${result.serverVersion}. Por favor actualice."
                is CheckVersionUseCase.VersionStatus.LocalSuperior ->
                    "Version de pruebas detectada (${result.localVersion})."
                else -> null
            }
            _state.value = _state.value.copy(versionMessage = message)
        }
    }
}
