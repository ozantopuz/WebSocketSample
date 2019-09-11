package com.app.websocketsample.core.dagger.module

import com.app.websocketsample.BuildConfig
import com.app.websocketsample.core.interceptor.HeaderInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    @Named("Endpoint")
    internal fun provideEndpoint(): HttpUrl = HttpUrl.parse(BuildConfig.API_ENDPOINT)!!


    @Provides
    internal fun provideHttpClient(interceptor: HeaderInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)

        return builder.build()
    }

    @Provides
    internal fun provideHeaderInterceptor(): HeaderInterceptor {
        return HeaderInterceptor()
    }

    @Provides
    internal fun provideConverter(): Converter.Factory = GsonConverterFactory.create(Gson())

    @Provides
    internal fun provideRxCallAdapterFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.create()
    }

    @Provides
    internal fun provideRestAdapterBuilder(client: OkHttpClient, converterFactory: Converter.Factory, factory: CallAdapter.Factory): Retrofit.Builder {
        return Retrofit.Builder()
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(factory)
    }

    @Provides
    fun provideRetrofit(@Named("Endpoint") endpoint: HttpUrl, builder: Retrofit.Builder): Retrofit {
        return builder.baseUrl(endpoint).build()
    }
}