package com.app.websocketsample.scene

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.app.websocketsample.app.App
import com.app.websocketsample.core.TestSchedulers
import com.app.websocketsample.core.entity.BaseEntity
import com.app.websocketsample.core.rx.SchedulerProvider
import com.app.websocketsample.data.entity.MockResponse
import com.app.websocketsample.data.repository.MockRepository
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @Mock
    private lateinit var repository: MockRepository
    private lateinit var app: App
    private lateinit var schedulerProvider: SchedulerProvider
    private lateinit var viewModel: MainViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUpDashboardViewModel() {
        MockitoAnnotations.initMocks(this)
        app = App()
        schedulerProvider = TestSchedulers()
        viewModel = MainViewModel(app, repository, schedulerProvider)
    }

    @Test
    fun test_getMocks_response_success() {
        whenever(repository.getMocks()).thenReturn(Observable.just(mockList))

        viewModel.getMocks()

        Mockito.verify<MockRepository>(repository).getMocks()
    }

    @Test
    fun test_getMocks_response_failed() {
        whenever(repository.getMocks()).thenReturn(Observable.error(Throwable("Error!")))

        viewModel.getMocks()

        Mockito.verify<MockRepository>(repository).getMocks()
    }

    companion object {
        private val mockList = BaseEntity(arrayListOf(MockResponse(1, "Mock1")))
    }
}