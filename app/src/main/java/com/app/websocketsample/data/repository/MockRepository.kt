package com.app.websocketsample.data.repository

import com.app.websocketsample.data.entity.Mock
import io.reactivex.Observable

interface MockRepository {

    fun getMocks(): Observable<List<Mock>>

}