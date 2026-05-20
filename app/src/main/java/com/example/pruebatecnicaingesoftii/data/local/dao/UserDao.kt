package com.example.pruebatecnicaingesoftii.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pruebatecnicaingesoftii.data.local.entity.UserEntity

/**
 * Operaciones de persistencia para la sesión de usuario.
 */
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSession(user: UserEntity)

    @Query("SELECT * FROM user_session LIMIT 1")
    suspend fun getSession(): UserEntity?

    @Query("DELETE FROM user_session")
    suspend fun clearSession()
}
