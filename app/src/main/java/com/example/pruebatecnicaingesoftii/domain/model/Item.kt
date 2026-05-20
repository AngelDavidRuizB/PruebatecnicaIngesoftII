package com.example.pruebatecnicaingesoftii.domain.model

/**
 * Modelo de dominio que representa la entidad central de la lógica de negocio.
 * Esta clase es independiente de cualquier framework o librería externa (SOLID: ISP).
 */
data class Item(
    val id: Int,
    val title: String,
    val description: String
)
