package com.app.websocketsample.data.repository

import com.app.websocketsample.core.entity.BaseEntity
import com.app.websocketsample.data.entity.Mock
import com.app.websocketsample.data.service.MockApi
import io.reactivex.Observable
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor(private val api: MockApi): MockRepository {

    override fun getMocks(): Observable<BaseEntity<Mock>> {
        return api.getMocks().toObservable()
    }
}