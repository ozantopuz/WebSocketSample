package com.app.websocketsample.core.dagger.module

import com.app.websocketsample.core.dagger.scope.ActivityScoped
import com.app.websocketsample.scene.MainActivity
import com.app.websocketsample.scene.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun mainActivity(): MainActivity
}