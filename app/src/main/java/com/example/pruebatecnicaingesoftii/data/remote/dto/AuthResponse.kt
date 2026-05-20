package com.example.pruebatecnicaingesoftii.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Respuesta del endpoint de Autenticación con campos para sesión.
 */
data class AuthResponse(
    @SerializedName("Token") val token: String?,
    @SerializedName("EsValido") val esValido: Boolean,
    @SerializedName("Mensaje") val mensaje: String?,
    @SerializedName("Usuario") val usuario: String?,
    @SerializedName("Identificacion") val identificacion: String?,
    @SerializedName("Nombre") val nombre: String?
)
