package com.app.websocketsample.scene

import com.app.websocketsample.app.App
import com.app.websocketsample.core.mvvm.BaseViewModel
import com.app.websocketsample.core.rx.RxBus
import com.app.websocketsample.core.rx.RxEvent
import com.app.websocketsample.data.entity.Mock
import com.app.websocketsample.data.repository.MockRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject



class MainViewModel @Inject constructor(
    private val app: App,
    private val repository : MockRepository): BaseViewModel() {

    var receivedMessageSubject : PublishSubject<String> = PublishSubject.create()

    override fun onViewAttached() {
        RxBus.listen(RxEvent.Message::class.java)
            .map { it.toString() }
            .subscribe {
                receivedMessageSubject.onNext(it)
            }.addTo(disposeBag)
    }

    class Inputs(
        val messageText: Observable<String>,
        val sendButtonTap: Observable<Unit>
    )

    class Outputs(
        val mocks: Observable<List<Mock>>,
        val isLoading: Observable<Boolean>,
        val error: Observable<String?>,
        val clearEditText: Observable<Unit>,
        val updateList: Observable<List<Mock>>
    )

    fun makeOutputFrom(inputs: Inputs): Outputs {

        val isLoadingSubject: PublishSubject<Boolean> = PublishSubject.create()
        val errorSubject: PublishSubject<String?> = PublishSubject.create()

        val messageText = inputs.messageText.map { it }

        val clearEditText = inputs.sendButtonTap
            .withLatestFrom(messageText)
            .map { it.second }
            .doOnNext { app.sendMessage(it) }
            .map { Unit }


        val mocks = Observable.just(Unit)
            .doOnNext { isLoadingSubject.onNext(true) }
            .flatMap {
                getMocks()
                    .doOnError { error ->
                        isLoadingSubject.onNext(false)
                        errorSubject.onNext(error.localizedMessage)
                    }
                    .onErrorResumeNext(Observable.empty())
            }
            .doOnNext {
                isLoadingSubject.onNext(false)
            }
            .map { it }

        val updateList = receivedMessageSubject
            .flatMap { getMocks() }
            .map { it.reversed() }

        return Outputs(
            mocks,
            isLoadingSubject,
            errorSubject,
            clearEditText,
            updateList
        )
    }

    private fun getMocks(): Observable<List<Mock>> = repository.getMocks()

    class MockViewModel(val id: String, val name: String)
}