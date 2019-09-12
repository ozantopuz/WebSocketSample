package com.app.websocketsample.app

import com.app.websocketsample.BuildConfig
import com.app.websocketsample.core.dagger.component.DaggerApplicationComponent
import com.app.websocketsample.data.socket.WebSocketCallBack
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.concurrent.TimeUnit

class App : DaggerApplication() {

    private var socket: WebSocket? = null

    override fun onCreate() {
        super.onCreate()
        initSocket()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }

    private fun initSocket(){
        val client = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .build()

        val request = Request.Builder()
            .url(BuildConfig.WEB_SOCKET_ENDPOINT)
            .build()

        socket = client.newWebSocket(request, WebSocketCallBack())
    }

    fun sendMessage(message: String){
        socket?.send(message)
    }
}