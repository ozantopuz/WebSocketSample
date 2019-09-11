package com.app.websocketsample.core.mvvm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

interface ViewModelLifecycle: LifecycleObserver {
    fun attachView(lifecycle: Lifecycle)
}