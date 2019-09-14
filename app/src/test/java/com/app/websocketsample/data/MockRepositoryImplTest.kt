package com.app.websocketsample.data

import com.app.websocketsample.core.entity.BaseEntity
import com.app.websocketsample.data.entity.MockResponse
import com.app.websocketsample.data.repository.MockRepositoryImpl
import com.app.websocketsample.data.service.MockService
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MockRepositoryImplTest {

    @Mock
    private lateinit var service: MockService
    private lateinit var repository: MockRepositoryImpl

    @Before
    fun setUpRepository(){
        MockitoAnnotations.initMocks(this)
        repository = MockRepositoryImpl(service)
    }

    @Test
    fun test_getMocks_response(){
        whenever(service.getMocks()).thenReturn(Observable.just(mockList))

        repository.getMocks()

        Mockito.verify<MockService>(service).getMocks()
    }

    companion object {
        private val mockList = BaseEntity(arrayListOf(MockResponse(1, "Mock1")))
    }
}