package com.example.codingchallenge.Network

import com.example.codingchallenge.Data.User
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query


interface CodingChallengeService {

    @GET("users")
    fun getUserByPageNo(@Query("page") pageNo: Int): Observable<Response<ArrayList<User>>>

    @GET("users/{id}")
    fun getUsersByID(@Path("id") id: Int): Observable<User>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id: Int, @Body user: User): Observable<Response<Void>>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): Observable<Response<Void>>

}