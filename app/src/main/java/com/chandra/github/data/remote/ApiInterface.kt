package com.chandra.github.data.remote


import com.chandra.github.BuildConfig
import com.chandra.github.data.model.UserResponse
import com.chandra.github.data.model.UserSearchData
import com.chandra.github.data.model.UserSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    companion object {

        private const val AUTH = BuildConfig.API_KEY
    }


    @GET("search/users")
    @Headers(AUTH)
   fun getSearchUser(
        @Query("q") query: String
    ):Call<UserSearchResponse>

    @GET("users/{username}")
    @Headers(AUTH)
    fun getDetailUser(
        @Path("username") username:String
    ):Call<UserResponse>

    @GET("users/{username}/followers")
    @Headers(AUTH)
    fun getListFollowers(
        @Path("username") username: String
    ):Call<List<UserSearchData>>

    @GET("users/{username}/following")
    @Headers(AUTH)
    fun getListFollowing(
        @Path("username") username: String
    ):Call<List<UserSearchData>>



}