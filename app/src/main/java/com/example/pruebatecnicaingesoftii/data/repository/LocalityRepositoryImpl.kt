package com.example.pruebatecnicaingesoftii.data.repository

import com.example.pruebatecnicaingesoftii.core.network.NetworkResult
import com.example.pruebatecnicaingesoftii.data.local.dao.LocalityDao
import com.example.pruebatecnicaingesoftii.data.local.dao.SchemaDao
import com.example.pruebatecnicaingesoftii.data.local.entity.LocalityEntity
import com.example.pruebatecnicaingesoftii.data.local.entity.SchemaTableEntity
import com.example.pruebatecnicaingesoftii.data.remote.RemoteDataSource
import com.example.pruebatecnicaingesoftii.domain.repository.LocalityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementacion del repositorio de localidades.
 * Aplica el patron "Network Bound Resource" simplificado.
 */
class LocalityRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localityDao: LocalityDao,
    private val schemaDao: SchemaDao
) : LocalityRepository {

    override suspend fun syncLocalities(): NetworkResult<Unit> {
        val result = remoteDataSource.getLocalities()

        return when (result) {
            is NetworkResult.Success -> {
                val dtos = result.data
                val entities = dtos.map {
                    LocalityEntity(
                        name = it.nombreCompleto ?: "Sin nombre",
                        code = it.abreviacionCiudad ?: "Sin codigo"
                    )
                }
                if (entities.isNotEmpty()) {
                    localityDao.deleteAll()
                    localityDao.insertLocalities(entities)
                }
                NetworkResult.Success(Unit)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.code, result.message)
            is NetworkResult.Exception -> NetworkResult.Exception(result.e)
        }
    }

    override fun getLocalLocalities(): Flow<List<LocalityEntity>> {
        return localityDao.getAllLocalities()
    }

    override suspend fun syncSchema(): NetworkResult<Unit> {
        val result = remoteDataSource.getScheme()
        return when (result) {
            is NetworkResult.Success -> {
                val rawList = result.data
                val tables = rawList.mapNotNull { item ->
                    when (item) {
                        is String -> SchemaTableEntity(tableName = item, description = null)
                        is Map<*, *> -> {
                            val name = item["TableName"] as? String
                                ?: item["tableName"] as? String
                                ?: item.toString()
                            SchemaTableEntity(tableName = name, description = item.toString())
                        }
                        else -> SchemaTableEntity(tableName = item.toString(), description = null)
                    }
                }
                schemaDao.clearTables()
                schemaDao.insertTables(tables)
                NetworkResult.Success(Unit)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.code, result.message)
            is NetworkResult.Exception -> NetworkResult.Exception(result.e)
        }
    }
}
