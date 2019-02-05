package com.baktiyar.android.jardamberem

import android.app.Application
import com.baktiyar.android.jardamberem.utils.ApiClient
import com.baktiyar.android.jardamberem.utils.ForumService

class StartApplication : Application() {
    companion object {
        @Volatile lateinit var INSTANCE: StartApplication
    }
    private val URL = "http://138.68.166.31:9000/"

    lateinit var service: ForumService

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        service = ApiClient.initRetrofit(URL, this)
    }

}