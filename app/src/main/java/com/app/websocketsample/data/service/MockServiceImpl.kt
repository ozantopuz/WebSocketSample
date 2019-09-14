package com.app.websocketsample.data.service

import com.app.websocketsample.core.entity.BaseEntity
import com.app.websocketsample.data.entity.MockResponse
import io.reactivex.Observable
import javax.inject.Inject

class MockServiceImpl @Inject constructor(private val api: MockApi): MockService {

    override fun getMocks(): Observable<BaseEntity<MockResponse>> {
        return api.getMocks()
    }
}