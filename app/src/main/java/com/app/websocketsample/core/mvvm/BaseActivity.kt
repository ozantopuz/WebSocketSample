package com.app.websocketsample.core.mvvm

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseActivity<B: ViewDataBinding, T : BaseViewModel>: DaggerAppCompatActivity(),
    LifecycleRegistryOwner {

    var lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry;
    }

    val disposeBag: CompositeDisposable = CompositeDisposable()

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