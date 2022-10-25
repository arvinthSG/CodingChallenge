package com.example.codingchallenge.Network

import com.example.codingchallenge.Data.User
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CodingChallengeService {

    @GET("users")
    fun getUserByPageNo(@Query("page")pageNo: Int): Observable<ArrayList<User>>

//    @GET("users")
//    fun getUserByID(@Query("id")id: Int): Observable<ArrayList<User>>

    @GET("users/{id}")
    fun getUsersByID(@Path("id")id: Int): Observable<User>
}