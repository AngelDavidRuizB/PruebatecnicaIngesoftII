package com.example.pruebatecnicaingesoftii.data.remote

import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import retrofit2.Response

/**
 * Clase base para manejar llamadas de red de forma segura.
 * Implementa el manejo de Try/Catch y control de errores HTTP.
 */
abstract class BaseRemoteDataSource {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return NetworkResult.Success(body)
            }
            return NetworkResult.Error(response.code(), response.message())
        } catch (e: Exception) {
            return NetworkResult.Exception(e)
        }
    }
}
