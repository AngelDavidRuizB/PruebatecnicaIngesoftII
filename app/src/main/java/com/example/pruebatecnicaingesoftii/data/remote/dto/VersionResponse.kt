package com.example.pruebatecnicaingesoftii.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Respuesta del endpoint de Control de Versiones.
 */
data class VersionResponse(
    @SerializedName("Version") val version: String? = null
)
