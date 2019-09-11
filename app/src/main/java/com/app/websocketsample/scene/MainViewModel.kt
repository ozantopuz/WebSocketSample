package com.app.websocketsample.scene

import androidx.lifecycle.MutableLiveData
import com.app.websocketsample.core.mvvm.BaseViewModel
import com.app.websocketsample.data.entity.Mock
import com.app.websocketsample.data.repository.MockRepository
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository : MockRepository): BaseViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var mocks = MutableLiveData<List<Mock>>()

    override fun onViewAttached() {
        getMocks()
    }

    private fun getMocks(){
        repository.getMocks()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .doOnSubscribe { isLoading.value = true }
            .subscribe {
                isLoading.value = false
                mocks.value = it
            }.addTo(disposeBag)
    }

    class MockViewModel(val id: String, val name: String)
}