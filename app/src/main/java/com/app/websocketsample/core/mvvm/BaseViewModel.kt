package com.app.websocketsample.core.mvvm

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel(), ViewModelLifecycle {

    var lifecycle: Lifecycle? = null

    val disposeBag: CompositeDisposable = CompositeDisposable()

    override fun attachView(lifecycle: Lifecycle) {
        this.lifecycle = lifecycle
        onViewAttached()
    }

    @Synchronized
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected fun onViewDestroyed() {
        clearDisposeBag()
        lifecycle = null
    }

    abstract fun onViewAttached()

    override fun onCleared() {
        clearDisposeBag()
        super.onCleared()
    }

    private fun clearDisposeBag() {
        disposeBag.clear()
    }
}