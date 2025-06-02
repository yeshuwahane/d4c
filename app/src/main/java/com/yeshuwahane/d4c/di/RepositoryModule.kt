package com.yeshuwahane.d4c.di

import com.yeshuwahane.d4c.data.repositoryimpl.AuthRepositoryImpl
import com.yeshuwahane.d4c.data.repositoryimpl.ProductRepositoryImpl
import com.yeshuwahane.d4c.data.repositoryimpl.TicketRepositoryImpl
import com.yeshuwahane.d4c.domain.repository.AuthRepository
import com.yeshuwahane.d4c.domain.repository.ProductRepository
import com.yeshuwahane.d4c.domain.repository.TicketRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideAuthRepo(
        impl: AuthRepositoryImpl
    ):AuthRepository


    @Binds
    @Singleton
    abstract fun provideProductRepo(
        impl: ProductRepositoryImpl
    ):ProductRepository

    @Binds
    @Singleton
    abstract fun provideTicketRepo(
        impl: TicketRepositoryImpl
    ):TicketRepository

}