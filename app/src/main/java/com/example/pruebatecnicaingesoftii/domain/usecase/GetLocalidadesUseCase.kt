package com.example.pruebatecnicaingesoftii.domain.usecase

import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import com.example.pruebatecnicaingesoftii.data.remote.RemoteDataSource
import com.example.pruebatecnicaingesoftii.data.remote.dto.LocalityDto
import javax.inject.Inject

/**
 * Caso de uso para obtener las localidades desde el servicio remoto.
 */
class GetLocalidadesUseCase @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) {
    suspend operator fun invoke(): NetworkResult<List<LocalityDto>> {
        return remoteDataSource.getLocalities()
    }
}
