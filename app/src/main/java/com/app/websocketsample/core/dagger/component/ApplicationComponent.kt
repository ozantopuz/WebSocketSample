package com.app.websocketsample.core.dagger.component

import com.app.websocketsample.app.App
import com.app.websocketsample.core.dagger.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    NetworkModule::class,
    ViewModelModule::class,
    ActivityBindingModule::class,
    ApiModule::class,
    ServiceModule::class,
    RepositoryModule::class
])

interface ApplicationComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: App): Builder

        fun build(): ApplicationComponent
    }
}