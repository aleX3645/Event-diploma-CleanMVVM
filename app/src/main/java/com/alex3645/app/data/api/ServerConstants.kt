package com.alex3645.app.data.api

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class ServerConstants {
    companion object{
        private const val LOCAL_SERVER_IP = "springandroidappconforoom-env.eba-83ujx6em.eu-central-1.elasticbeanstalk.com/"
        private const val LOCAL_SERVER_PORT = "5000"
        const val LOCAL_SERVER: String = "http://${LOCAL_SERVER_IP}"
        const val WEB_SOCKET_LOCAL_SERVER: String = "ws://${LOCAL_SERVER_IP}"

        @SuppressLint("SimpleDateFormat")
        val serverDateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    }
}