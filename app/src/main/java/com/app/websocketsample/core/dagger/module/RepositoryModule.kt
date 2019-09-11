package com.app.websocketsample.core.dagger.module

import com.app.websocketsample.data.repository.MockRepository
import com.app.websocketsample.data.repository.MockRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideMockRepository(repository: MockRepositoryImpl): MockRepository
}