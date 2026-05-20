package com.example.pruebatecnicaingesoftii.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pruebatecnicaingesoftii.data.local.entity.SchemaTableEntity

/**
 * Operaciones de persistencia para el esquema de tablas.
 */
@Dao
interface SchemaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTables(tables: List<SchemaTableEntity>)

    @Query("SELECT * FROM schema_tables")
    suspend fun getAllTables(): List<SchemaTableEntity>

    @Query("DELETE FROM schema_tables")
    suspend fun clearTables()
}
