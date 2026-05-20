package com.example.pruebatecnicaingesoftii.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pruebatecnicaingesoftii.data.local.entity.LocalityEntity
import kotlinx.coroutines.flow.Flow

/**
 * DAO para realizar operaciones sobre la tabla de localidades.
 */
@Dao
interface LocalityDao {

    @Query("SELECT * FROM localities")
    fun getAllLocalities(): Flow<List<LocalityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocalities(localities: List<LocalityEntity>)

    @Query("DELETE FROM localities")
    suspend fun deleteAll()
}
