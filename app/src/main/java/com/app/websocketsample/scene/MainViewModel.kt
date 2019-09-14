package com.app.websocketsample.scene

import com.app.websocketsample.app.App
import com.app.websocketsample.core.extension.createMockObject
import com.app.websocketsample.core.extension.isLogout
import com.app.websocketsample.core.extension.isMessageFormat
import com.app.websocketsample.core.mvvm.BaseViewModel
import com.app.websocketsample.core.rx.RxBus
import com.app.websocketsample.core.rx.RxEvent
import com.app.websocketsample.core.rx.SchedulerProvider
import com.app.websocketsample.data.entity.MockResponse
import com.app.websocketsample.data.repository.MockRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.withLatestFrom
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject



class MainViewModel @Inject constructor(
    private val app: App,
    private val repository : MockRepository,
    private val schedulers: SchedulerProvider
): BaseViewModel() {

    private val receivedMessageSubject : PublishSubject<String> = PublishSubject.create()
    private var mocks : MutableList<MockResponse> = mutableListOf()

    override fun onViewAttached() {
        RxBus.listen(RxEvent.Message::class.java)
            .map { it.message.toString() }
            .subscribe {
                receivedMessageSubject.onNext(it)
            }.addTo(disposeBag)
    }

    class Inputs(
        val messageText: Observable<String>,
        val sendButtonTap: Observable<Unit>
    )

    class Outputs(
        val showList: Observable<List<MockResponse>>,
        val isLoading: Observable<Boolean>,
        val error: Observable<String?>,
        val login: Observable<String>,
        val logout: Observable<Unit>,
        val clearEditText: Observable<Unit>,
        val updateList: Observable<List<MockResponse>>
    )

    fun makeOutputFrom(inputs: Inputs): Outputs {

        val isLoadingSubject: PublishSubject<Boolean> = PublishSubject.create()
        val errorSubject: PublishSubject<String?> = PublishSubject.create()

        val messageText = inputs.messageText
            .share()

        val sendButtonTap = inputs.sendButtonTap
            .share()

        val logout = sendButtonTap
            .withLatestFrom(messageText)
            .filter { it.second.isLogout() }
            .map { Unit }

        val clearEditText = sendButtonTap
            .withLatestFrom(messageText)
            .map { it.second }
            .doOnNext { if (it.isMessageFormat()) app.sendMessage(it) }
            .map { Unit }

        val showList = Observable.just(Unit)
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
                mocks = it.toMutableList()
            }

        val receivedMessage = receivedMessageSubject
            .map { it }
            .share()

        val login = receivedMessage.map { it.createMockObject().name.toString() }

        val updateList = receivedMessage
            .filter { it.isMessageFormat() }
            .map { it.createMockObject() }
            .map {
                mocks.find { list -> list.id == it.id }?.name = it.name
                mocks.toList()
            }

        return Outputs(
            showList,
            isLoadingSubject,
            errorSubject,
            login,
            logout,
            clearEditText,
            updateList
        )
    }

    fun getMocks(): Observable<List<MockResponse>> {
        return repository.getMocks()
            .subscribeOn(schedulers.io())
            .observeOn(schedulers.main())
            .map { it.data ?: listOf() }
    }

    class MockViewModel(val id: String, val name: String)
}