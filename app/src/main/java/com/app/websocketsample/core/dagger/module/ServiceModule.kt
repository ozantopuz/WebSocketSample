package com.app.websocketsample.core.dagger.module

import com.app.websocketsample.data.service.MockService
import com.app.websocketsample.data.service.MockServiceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ServiceModule {

    @Binds
    abstract fun provideMockService(mockService: MockServiceImpl): MockService
}