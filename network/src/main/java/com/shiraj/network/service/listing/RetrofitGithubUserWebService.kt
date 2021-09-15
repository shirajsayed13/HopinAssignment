package com.shiraj.network.service.listing

import com.shiraj.network.response.GithubUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface RetrofitGithubUserWebService {

    @GET("search/users")
    suspend fun getGithubUsers(
        @Query("q", encoded = true) query: String,
        @Query("per_page", encoded = true) perPage: String,
        @Query("page", encoded = true) page: String,
    ): GithubUserResponse
}