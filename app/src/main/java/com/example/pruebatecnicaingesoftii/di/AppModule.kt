package com.example.pruebatecnicaingesoftii.di

import android.content.Context
import androidx.room.Room
import com.example.pruebatecnicaingesoftii.data.local.AppDatabase
import com.example.pruebatecnicaingesoftii.data.local.dao.LocalityDao
import com.example.pruebatecnicaingesoftii.data.local.dao.UserDao
import com.example.pruebatecnicaingesoftii.data.local.dao.SchemaDao
import com.example.pruebatecnicaingesoftii.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Módulo de Hilt para proveer dependencias a nivel de aplicación (Network y Database).
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://apitesting.interrapidisimo.co/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ingesoft_db"
        ).build()
    }

    @Provides
    fun provideLocalityDao(database: AppDatabase): LocalityDao {
        return database.localityDao()
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideSchemaDao(database: AppDatabase): SchemaDao {
        return database.schemaDao()
    }
}
