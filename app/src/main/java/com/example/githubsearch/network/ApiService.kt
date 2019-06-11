package com.example.githubsearch.network

import com.example.githubsearch.model.UsersResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getUsersGithub(@Query("q") name : String,
                       @Query("per_page") page : String) : Observable<UsersResponse>
}