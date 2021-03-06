package com.app.websocketsample.data.repository

import com.app.websocketsample.core.entity.BaseEntity
import com.app.websocketsample.data.entity.MockResponse
import com.app.websocketsample.data.service.MockService
import io.reactivex.Observable
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor(private val service: MockService): MockRepository {

    override fun getMocks(): Observable<BaseEntity<MockResponse>> {
        return service.getMocks()
    }
}