package com.app.websocketsample.core.dagger.module

import android.content.Context
import com.app.websocketsample.app.App
import com.app.websocketsample.core.rx.AppSchedulers
import com.app.websocketsample.core.rx.SchedulerProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(androidApplication: App): Context

    @Binds
    abstract fun bindSchedulers(scheduler: AppSchedulers): SchedulerProvider
}