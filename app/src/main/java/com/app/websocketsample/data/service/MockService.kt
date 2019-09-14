package com.app.websocketsample.data.service

import com.app.websocketsample.core.entity.BaseEntity
import com.app.websocketsample.data.entity.MockResponse
import io.reactivex.Observable

interface MockService {

    fun getMocks(): Observable<BaseEntity<MockResponse>>

}