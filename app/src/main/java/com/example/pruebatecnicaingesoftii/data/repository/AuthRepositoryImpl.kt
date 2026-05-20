package com.example.pruebatecnicaingesoftii.data.repository

import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import com.example.pruebatecnicaingesoftii.data.local.dao.UserDao
import com.example.pruebatecnicaingesoftii.data.local.entity.UserEntity
import com.example.pruebatecnicaingesoftii.data.remote.RemoteDataSource
import com.example.pruebatecnicaingesoftii.data.remote.dto.AuthRequest
import com.example.pruebatecnicaingesoftii.domain.repository.AuthRepository
import javax.inject.Inject

/**
 * Implementación del repositorio de autenticación.
 * Maneja la persistencia automática de la sesión al recibir un HTTP 200.
 */
class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val userDao: UserDao
) : AuthRepository {

    override suspend fun login(): NetworkResult<UserEntity> {
        val result = remoteDataSource.authenticateUser(AuthRequest())

        return when (result) {
            is NetworkResult.Success -> {
                val response = result.data
                // Extraemos campos si no son nulos
                val userEntity = UserEntity(
                    identificacion = response.identificacion ?: "",
                    usuario = response.usuario ?: "",
                    nombre = response.nombre ?: ""
                )
                
                // Persistencia local de la sesión (SOLID: DIP)
                userDao.saveSession(userEntity)
                
                NetworkResult.Success(userEntity)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.code, result.message)
            is NetworkResult.Exception -> NetworkResult.Exception(result.e)
        }
    }

    override suspend fun getSavedSession(): UserEntity? {
        return userDao.getSession()
    }
}
