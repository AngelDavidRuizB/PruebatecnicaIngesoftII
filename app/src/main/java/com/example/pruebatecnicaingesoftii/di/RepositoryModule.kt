package com.example.pruebatecnicaingesoftii.di

import com.example.pruebatecnicaingesoftii.data.repository.AuthRepositoryImpl
import com.example.pruebatecnicaingesoftii.data.repository.ItemRepositoryImpl
import com.example.pruebatecnicaingesoftii.data.repository.LocalityRepositoryImpl
import com.example.pruebatecnicaingesoftii.domain.repository.AuthRepository
import com.example.pruebatecnicaingesoftii.domain.repository.ItemRepository
import com.example.pruebatecnicaingesoftii.domain.repository.LocalityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo de Hilt para vincular interfaces con sus implementaciones.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindItemRepository(
        itemRepositoryImpl: ItemRepositoryImpl
    ): ItemRepository

    @Binds
    @Singleton
    abstract fun bindLocalityRepository(
        localityRepositoryImpl: LocalityRepositoryImpl
    ): LocalityRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository
}
