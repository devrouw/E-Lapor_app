package com.lollipop.e_lapor.service.network

import android.content.Context
import com.lollipop.e_lapor.view.ApplicationController
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private val okHttpClient =
    OkHttpClient.Builder()
        .addNetworkInterceptor(ChuckInterceptor(ApplicationController.appContext))
        .build()

private val retrofitCall = Retrofit.Builder()
    .baseUrl("http://elapor.sha-dev.com/android/")
    .addConverterFactory(MoshiConverterFactory.create())
    .client(okHttpClient)
    .build()

class RetrofitClient(context: Context) {
    companion object Retrofit {
        /**
         * http://ensiklopedia.sha-dev.com/
         * */
        val ftp: ShaNetwork = retrofitCall.create(ShaNetwork::class.java)
    }
}