package com.example.pruebatecnicaingesoftii.data.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * Peticion de Autenticacion para el endpoint AuthenticaUsuarioApp.
 */
data class AuthRequest(
    @SerializedName("Mac") val mac: String = "",
    @SerializedName("NomAplicacion") val nomAplicacion: String = "Controller APP",
    @SerializedName("Password") val password: String = "SW50ZXIyMDIx\n",
    @SerializedName("Path") val path: String = "",
    @SerializedName("Usuario") val usuario: String = "cGFtLm1lcmVkeTIx\n"
)
