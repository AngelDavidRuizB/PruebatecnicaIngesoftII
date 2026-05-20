package com.example.pruebatecnicaingesoftii.core.util

/**
 * Clase sellada (Sealed Class) para manejar los estados de la UI y las respuestas de datos.
 * Aplica el principio de diseño de envolver el estado para manejar éxito, error y carga.
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
