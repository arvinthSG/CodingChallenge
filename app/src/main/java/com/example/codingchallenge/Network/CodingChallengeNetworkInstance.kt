package com.example.codingchallenge.Network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class CodingChallengeNetworkInstance {

    companion object {
        private const val BASE_URL = "https://gorest.co.in/public/v2/"
        private const val ACCESS_TOKEN = "f70ebbee10592ff681ab9aa8f0217fae947e6ab09a8f0eb8218a049eac8096f4"

        var client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $ACCESS_TOKEN")
                .build()
            chain.proceed(newRequest)
        }.build()

        fun provideNetworkInstance(): Retrofit {
            val gson = GsonBuilder().create()
            return Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}