package com.example.pruebatecnicaingesoftii.data.remote

import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import com.example.pruebatecnicaingesoftii.data.remote.api.ApiService
import com.example.pruebatecnicaingesoftii.data.remote.dto.AuthRequest
import com.example.pruebatecnicaingesoftii.data.remote.dto.AuthResponse
import javax.inject.Inject

/**
 * Fuente de datos remota que utiliza ApiService.
 * Aplica el manejo de errores específico para autenticación.
 */
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : BaseRemoteDataSource() {

    suspend fun authenticateUser(request: AuthRequest): NetworkResult<AuthResponse> {
        val result = safeApiCall { apiService.authenticate(request) }
        
        if (result is NetworkResult.Error && result.code != 200) {
            return NetworkResult.Error(result.code, "Alerta de Seguridad: Error en autenticación (${result.code})")
        }
        return result
    }

    suspend fun checkVersion() = safeApiCall { apiService.getAppVersion() }
    
    suspend fun getLocalities() = safeApiCall { apiService.getLocalities() }
    
    suspend fun getScheme() = safeApiCall { apiService.getScheme() }
}
