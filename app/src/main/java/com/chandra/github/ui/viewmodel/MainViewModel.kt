package com.chandra.github.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chandra.github.data.remote.ApiClient
import com.chandra.github.data.remote.ApiInterface
import com.chandra.github.data.model.UserResponse
import com.chandra.github.data.model.UserSearchData
import com.chandra.github.data.model.UserSearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val _listFollowing = MutableLiveData<List<UserSearchData>>()
    val listFollowing: LiveData<List<UserSearchData>> = _listFollowing

    val _listFollowers = MutableLiveData<List<UserSearchData>>()
    val listFollowers: LiveData<List<UserSearchData>> = _listFollowers

    val _userData = MutableLiveData<UserResponse>()
    val userData: LiveData<UserResponse> = _userData

    val _userSearch = MutableLiveData<UserSearchResponse?>()
    val userSearch: LiveData<UserSearchResponse?> = _userSearch

    private fun getListFollowing(username: String) {
        val retro = ApiClient().getApiClient().create(ApiInterface::class.java)
        val retrofitData = retro.getListFollowing(username)
        retrofitData.enqueue(object : Callback<List<UserSearchData>?> {
            override fun onFailure(call: Call<List<UserSearchData>?>, t: Throwable) {
                Log.d("FOLLOWINGFRAGMENT", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<UserSearchData>?>,
                response: Response<List<UserSearchData>?>
            ) {
                val responseBody = response.body()
                _listFollowing.postValue(responseBody!!)
            }
        })
    }

    private fun getListFollowers(username: String) {
        val retro = ApiClient().getApiClient().create(ApiInterface::class.java)
        val retrofitData = retro.getListFollowers(username)
        retrofitData.enqueue(object : Callback<List<UserSearchData>?> {
            override fun onFailure(call: Call<List<UserSearchData>?>, t: Throwable) {
                Log.d("FOLLOWERFRAGMENT", t.message.toString())
            }

            override fun onResponse(
                call: Call<List<UserSearchData>?>,
                response: Response<List<UserSearchData>?>
            ) {
                val responseBody = response.body()
                _listFollowers.postValue(responseBody!!)
            }
        })
    }

    fun getUserData(username: String) {
        val retro = ApiClient().getApiClient().create(ApiInterface::class.java)
        val retrofitData = retro.getDetailUser(username)
        getListFollowers(username)
        getListFollowing(username)

        retrofitData.enqueue(object : Callback<UserResponse?> {
            override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                Log.d("DETAIL", t.message.toString())
            }
            override fun onResponse(call: Call<UserResponse?>, response: Response<UserResponse?>) {
                val responseBody = response.body()
                _userData.postValue(responseBody!!)

            }
        })
    }

   fun getUserSearch(username: String){
       Log.d("main",username.toString())
        val retro = ApiClient()
            .getApiClient().create(ApiInterface::class.java)
        val retrofitData = retro.getSearchUser(username)
        retrofitData.enqueue(object : Callback<UserSearchResponse?> {
            override fun onFailure(call: Call<UserSearchResponse?>, t: Throwable) {
                Log.d("SEARCH", t.message.toString())
            }

            override fun onResponse(
                call: Call<UserSearchResponse?>,
                response: Response<UserSearchResponse?>
            ) {
                val responseBody = response.body()
                Log.d("main",responseBody.toString())
                _userSearch.postValue(responseBody)
            }
        })
    }
}