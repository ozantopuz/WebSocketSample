package com.app.websocketsample.data.socket

import com.app.websocketsample.core.rx.RxBus
import com.app.websocketsample.core.rx.RxEvent
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebSocketCallBack : WebSocketListener() {

    override fun onMessage(webSocket: WebSocket?, text: String?) {
        RxBus.publish(RxEvent.Message(text))
    }
}