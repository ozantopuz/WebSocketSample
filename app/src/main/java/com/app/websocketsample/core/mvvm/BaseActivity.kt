package com.app.websocketsample.core.mvvm

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseActivity<B: ViewDataBinding, T : BaseViewModel>: DaggerAppCompatActivity() {

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun instantiateViewModel(): T

    abstract fun attachView()

    abstract fun setupView()

    abstract fun bindViewModel()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: T
        internal set

    lateinit var binding: B
        internal  set

    val disposeBag: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, layoutId())

        viewModel = instantiateViewModel()

        attachView()

        lifecycle.addObserver(viewModel)

        setupView()

        bindViewModel()
    }

    @Synchronized
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    protected fun onViewDestroyed() {
        clearDisposeBag()
    }

    private fun clearDisposeBag() {
        disposeBag.clear()
    }
}