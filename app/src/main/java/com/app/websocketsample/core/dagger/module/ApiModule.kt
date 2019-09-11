package com.app.websocketsample.core.dagger.module

import com.app.websocketsample.data.service.MockApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    internal fun provideMockApi(retrofit: Retrofit): MockApi {
        return retrofit.create(MockApi::class.java)
    }
}