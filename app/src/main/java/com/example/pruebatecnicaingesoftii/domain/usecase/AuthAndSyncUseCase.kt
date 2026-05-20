package com.example.pruebatecnicaingesoftii.domain.usecase

import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import com.example.pruebatecnicaingesoftii.domain.repository.AuthRepository
import com.example.pruebatecnicaingesoftii.domain.repository.LocalityRepository
import javax.inject.Inject

/**
 * Caso de uso que orquesta la autenticación (con persistencia local) 
 * y la sincronización de esquema y localidades.
 */
class AuthAndSyncUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val localityRepository: LocalityRepository
) {
    suspend operator fun invoke(): NetworkResult<String> {
        // 1. Autenticación (El repositorio guarda la sesión automáticamente al ser HTTP 200)
        val authResult = authRepository.login()
        
        return when (authResult) {
            is NetworkResult.Success -> {
                // 2. Sincronización de Esquema
                localityRepository.syncSchema()
                
                // 3. Sincronización de Localidades
                val syncResult = localityRepository.syncLocalities()
                
                if (syncResult is NetworkResult.Success) {
                    NetworkResult.Success("Sesión guardada y Sincronización completada.")
                } else {
                    NetworkResult.Success("Sesión guardada, pero hubo errores en la sincronización de datos.")
                }
            }
            is NetworkResult.Error -> NetworkResult.Error(authResult.code, authResult.message)
            is NetworkResult.Exception -> NetworkResult.Exception(authResult.e)
        }
    }
}
