package com.example.mygithubappsfix.user_interface.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubappsfix.api.ApiConfig
import com.example.mygithubappsfix.data.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel: ViewModel() {
    val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setFollowing(username: String) {
        ApiConfig.getApiService()
            .getUserFollowing(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) {
                        listFollowing.postValue(response.body())
                    }
                }
                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })
    }

    fun getFollowing() : LiveData<ArrayList<User>>{
        return listFollowing
    }
}