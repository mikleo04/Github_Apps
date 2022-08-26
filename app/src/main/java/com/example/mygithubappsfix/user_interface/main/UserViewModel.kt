package com.example.mygithubappsfix.user_interface.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubappsfix.api.ApiConfig
import com.example.mygithubappsfix.data.GithubResponse
import com.example.mygithubappsfix.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val listUsers = MutableLiveData<ArrayList<User>>()

    fun setSearchUser(query: String) {
        ApiConfig.getApiService()
            .getSearchUser(query)
            .enqueue(object : Callback<GithubResponse> {
                override fun onResponse(
                    call: Call<GithubResponse>,
                    response: Response<GithubResponse>
                ) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }

            })
    }

    fun getSearchUser(): LiveData<ArrayList<User>> {
        return listUsers
    }
}