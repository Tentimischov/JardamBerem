package com.baktiyar.android.jardamberem.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {
    val REQUEST_TIME = 60L

    fun initRetrofit(url: String): ForumService {
        return Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(initGson()))
                .client(getClient()).build()
                .create(ForumService::class.java)
    }

    private fun initGson(): Gson {
        return GsonBuilder().create()
    }

    private fun getClient(): OkHttpClient {
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