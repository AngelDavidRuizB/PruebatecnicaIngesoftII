package com.example.pruebatecnicaingesoftii.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pruebatecnicaingesoftii.data.local.dao.LocalityDao
import com.example.pruebatecnicaingesoftii.data.local.dao.UserDao
import com.example.pruebatecnicaingesoftii.data.local.dao.SchemaDao
import com.example.pruebatecnicaingesoftii.data.local.entity.ItemEntity
import com.example.pruebatecnicaingesoftii.data.local.entity.LocalityEntity
import com.example.pruebatecnicaingesoftii.data.local.entity.UserEntity
import com.example.pruebatecnicaingesoftii.data.local.entity.SchemaTableEntity

/**
 * Base de datos principal de la aplicación utilizando Room.
 * SOLID: OCP - Fácil de extender añadiendo nuevas entidades y DAOs.
 */
@Database(
    entities = [
        ItemEntity::class, 
        LocalityEntity::class, 
        UserEntity::class, 
        SchemaTableEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun localityDao(): LocalityDao
    abstract fun userDao(): UserDao
    abstract fun schemaDao(): SchemaDao
}
