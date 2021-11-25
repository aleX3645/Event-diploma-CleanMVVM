package com.alex3645.app.data.api

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class ServerConstants {
    companion object{
        private const val LOCAL_SERVER_IP = "100.113.76.244"
        private const val LOCAL_SERVER_PORT = "7777"
        const val LOCAL_SERVER: String = "http://${LOCAL_SERVER_IP}:${LOCAL_SERVER_PORT}"
        const val WEB_SOCKET_LOCAL_SERVER: String = "ws://${LOCAL_SERVER_IP}:${LOCAL_SERVER_PORT}"

        @SuppressLint("ConstantLocale")
        val serverDateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault())
    }
}