package com.app.websocketsample.data.repository

import com.app.websocketsample.core.rx.SchedulerProvider
import com.app.websocketsample.data.entity.Mock
import com.app.websocketsample.data.service.MockApi
import io.reactivex.Observable
import javax.inject.Inject

class MockRepositoryImpl @Inject constructor(
    private val api: MockApi,
    private val schedulers: SchedulerProvider): MockRepository {

    override fun getMocks(): Observable<List<Mock>> {
        return Observable.just(Unit)
            .flatMap { api.getMocks().toObservable() }
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .map { it.data ?: listOf() }
    }
}