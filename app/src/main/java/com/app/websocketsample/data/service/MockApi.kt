package com.app.websocketsample.data.service

import com.app.websocketsample.core.entity.BaseEntity
import com.app.websocketsample.data.entity.MockResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface MockApi {

    @GET("mock-api/db")
    fun getMocks(): Observable<BaseEntity<MockResponse>>
}