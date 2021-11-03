package com.alex3645.feature_conference_detail.data.network.socket

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener


class WebSocketListener(private val callBack: (message: String)->Unit) : WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response?) {
        //webSocket.
        Log.d("!!!", "OnOpen")
    }

    override fun onMessage(webSocket: WebSocket?, text: String) {
        Log.d("!!!", "OnMessage - $text")
        callBack.invoke(text)
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d("!!!", "OnClosing")
    }

    override fun onFailure(webSocket: WebSocket?, t: Throwable, response: Response?) {
        Log.d("!!!", "OnFailure - " + t.message)
    }

    companion object {
        private const val NORMAL_CLOSURE_STATUS = 1000
    }
}