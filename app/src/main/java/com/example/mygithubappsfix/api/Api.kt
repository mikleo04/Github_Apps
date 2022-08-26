package com.example.mygithubappsfix.api

import com.example.mygithubappsfix.data.GithubResponse
import com.example.mygithubappsfix.data.User
import com.example.mygithubappsfix.data.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}