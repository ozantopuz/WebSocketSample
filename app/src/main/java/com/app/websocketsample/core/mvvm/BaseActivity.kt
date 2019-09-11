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

abstract class BaseActivity<DB: ViewDataBinding, VM : BaseViewModel>: DaggerAppCompatActivity() {

    val disposeBag: CompositeDisposable = CompositeDisposable()

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun instantiateViewModel(): VM

    abstract fun attachView()

    abstract fun setupView()

    abstract fun bindViewModel()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: VM
        internal set

    lateinit var binding: DB
        internal  set

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