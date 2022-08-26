package com.example.mygithubappsfix.user_interface.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mygithubappsfix.api.ApiConfig
import com.example.mygithubappsfix.data.UserDetailResponse
import com.example.mygithubappsfix.data.local.UserDatabase
import com.example.mygithubappsfix.data.local.UserFavorite
import com.example.mygithubappsfix.data.local.UserFavoriteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application): AndroidViewModel(application) {
    val user = MutableLiveData<UserDetailResponse>()

    private var userDao: UserFavoriteDao?
    private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getDatabase(application)
        userDao = userDatabase?.userFavoriteDao()
    }

    fun setUserDetail(username: String) {
        ApiConfig.getApiService()
            .getUserDetail(username)
            .enqueue(object : Callback<UserDetailResponse> {
                override fun onResponse(
                    call: Call<UserDetailResponse>,
                    response: Response<UserDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                    Log.d("Failure", t.message!!)
                }
            })
    }

    fun getUserDetail(): LiveData<UserDetailResponse> {
        return user
    }

    fun addFavorite(username: String, id: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var user = UserFavorite(
                username,
                id,
                avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkuser(id)

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFromFavorite(id)
        }
    }
}