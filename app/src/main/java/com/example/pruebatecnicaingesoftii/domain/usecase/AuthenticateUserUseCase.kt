package com.example.pruebatecnicaingesoftii.domain.usecase

import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import com.example.pruebatecnicaingesoftii.data.local.entity.UserEntity
import com.example.pruebatecnicaingesoftii.domain.repository.AuthRepository
import com.example.pruebatecnicaingesoftii.domain.repository.LocalityRepository
import javax.inject.Inject

/**
 * Caso de uso para la autenticación de usuario.
 * Si es exitosa, el repositorio guarda el usuario y aquí disparamos la sincronización del esquema.
 */
class AuthenticateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val localityRepository: LocalityRepository
) {
    suspend operator fun invoke(): NetworkResult<UserEntity> {
        val authResult = authRepository.login()
        
        if (authResult is NetworkResult.Success) {
            // Disparamos la sincronización del esquema tras éxito en login
            localityRepository.syncSchema()
        }
        
        return authResult
    }
}
