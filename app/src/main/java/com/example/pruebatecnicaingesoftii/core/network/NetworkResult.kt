package com.example.pruebatecnicaingesoftii.core.network

/**
 * Wrapper para manejar estados de peticiones de red y errores.
 * Encapsula éxito, error HTTP y excepciones de red.
 */
sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error<T>(val code: Int, val message: String) : NetworkResult<T>()
    data class Exception<T>(val e: Throwable) : NetworkResult<T>()
}
