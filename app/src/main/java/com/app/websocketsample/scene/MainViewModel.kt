package com.app.websocketsample.scene

import androidx.lifecycle.MutableLiveData
import com.app.websocketsample.app.App
import com.app.websocketsample.core.mvvm.BaseViewModel
import com.app.websocketsample.data.entity.Mock
import com.app.websocketsample.data.repository.MockRepository
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val app: App,
    private val repository : MockRepository): BaseViewModel() {

    var isLoading = MutableLiveData<Boolean>()
    var mocks = MutableLiveData<List<Mock>>()
    var messageSubject : PublishSubject<String> = PublishSubject.create()

    override fun onViewAttached() {
        getMocks()

        messageSubject.subscribe {
            //send message
        }.addTo(disposeBag)
    }

    private fun getMocks() {
         repository.getMocks()
            .doOnNext { isLoading.value = true }
            .subscribe {
                mocks.value = it
                isLoading.value = false
            }.addTo(disposeBag)
    }

    class MockViewModel(val id: String, val name: String)
}