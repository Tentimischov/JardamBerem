package com.baktiyar.android.jardamberem.utils

import android.content.Context
import com.baktiyar.android.jardamberem.utils.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {
    val REQUEST_TIME = 60L

    fun initRetrofit(url: String, context: Context): ApiService {
        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(initGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getClient(context)).build()
                .create(ApiService::class.java)
    }

    private fun initGson(): Gson {
        return GsonBuilder().create()
    }

    private fun getClient(context: Context): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()

                    val request = original.newBuilder()
                            .method(original.method(), original.body())
                            .build()
                    return@addInterceptor chain.proceed(request)

                }
                .addInterceptor(interceptor)
                .writeTimeout(REQUEST_TIME, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIME, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIME, TimeUnit.SECONDS)
        return client.build()
    }
}