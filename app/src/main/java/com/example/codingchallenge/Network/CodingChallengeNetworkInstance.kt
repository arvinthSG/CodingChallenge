package com.example.codingchallenge.Network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CodingChallengeNetworkInstance {

    companion object {
        private const val BASE_URL = " https://gorest.co.in/public/v2/"

        fun provideNetworkInstance(): Retrofit {
            val gson = GsonBuilder().create()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}