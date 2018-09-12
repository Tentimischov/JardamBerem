package com.baktiyar.android.jardamberem

import android.app.Application
import com.baktiyar.android.jardamberem.utils.ApiClient
import com.baktiyar.android.jardamberem.utils.ForumService

class ApplicationClass : Application() {
    companion object {
        @Volatile var INSTANCE: ApplicationClass? = null
    }
    private val URL = "http://138.68.166.31:9000/"

    var service: ForumService? = null

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        service = ApiClient.initRetrofit(URL, this)
    }

}