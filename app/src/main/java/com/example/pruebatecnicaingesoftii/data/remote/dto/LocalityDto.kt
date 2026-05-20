package com.example.pruebatecnicaingesoftii.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para representar una localidad de la API.
 */
data class LocalityDto(
    @SerializedName("AbreviacionCiudad") val abreviacionCiudad: String?,
    @SerializedName("NombreCompleto") val nombreCompleto: String?
)
