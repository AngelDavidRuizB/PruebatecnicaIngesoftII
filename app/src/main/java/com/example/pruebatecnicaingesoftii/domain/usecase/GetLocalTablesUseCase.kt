package com.example.pruebatecnicaingesoftii.domain.usecase

import com.example.pruebatecnicaingesoftii.data.local.dao.SchemaDao
import com.example.pruebatecnicaingesoftii.data.local.entity.SchemaTableEntity
import javax.inject.Inject

/**
 * Caso de uso para obtener las tablas del esquema guardadas localmente.
 */
class GetLocalTablesUseCase @Inject constructor(
    private val schemaDao: SchemaDao
) {
    suspend operator fun invoke(): List<SchemaTableEntity> {
        return schemaDao.getAllTables()
    }
}
