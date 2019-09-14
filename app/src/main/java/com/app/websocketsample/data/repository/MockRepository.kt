package com.app.websocketsample.data.repository

import com.app.websocketsample.core.entity.BaseEntity
import com.app.websocketsample.data.entity.MockResponse
import io.reactivex.Observable

interface MockRepository {

    fun getMocks(): Observable<BaseEntity<MockResponse>>

}