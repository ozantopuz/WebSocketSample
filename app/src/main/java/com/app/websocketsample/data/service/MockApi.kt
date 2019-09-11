package com.app.websocketsample.data.service

import com.app.websocketsample.core.entity.BaseEntity
import com.app.websocketsample.data.entity.Mock
import io.reactivex.Flowable
import retrofit2.http.GET

interface MockApi {

    @GET("mock-api/db")
    fun getMocks(): Flowable<BaseEntity<Mock>>
}