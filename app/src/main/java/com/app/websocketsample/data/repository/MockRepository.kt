package com.app.websocketsample.data.repository

import com.app.websocketsample.core.entity.BaseEntity
import com.app.websocketsample.data.entity.Mock
import io.reactivex.Observable

interface MockRepository {

    fun getMocks(): Observable<BaseEntity<Mock>>

}