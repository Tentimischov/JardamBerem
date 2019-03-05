package com.baktiyar.android.jardamberem

import android.app.Application
import com.baktiyar.android.jardamberem.utils.ApiClient
import com.baktiyar.android.jardamberem.utils.ForumService

class StartApplication : Application() {
    companion object {
        @Volatile lateinit var INSTANCE: StartApplication
    }
    private val URL = "http://207.154.212.45/"

    lateinit var service: ForumService

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        service = ApiClient.initRetrofit(URL)
    }

}