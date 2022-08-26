package com.example.mygithubappsfix.user_interface.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mygithubappsfix.data.local.UserDatabase
import com.example.mygithubappsfix.data.local.UserFavorite
import com.example.mygithubappsfix.data.local.UserFavoriteDao

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: UserFavoriteDao?
    private var userDatabase: UserDatabase?

    init {
        userDatabase = UserDatabase.getDatabase(application)
        userDao = userDatabase?.userFavoriteDao()
    }

    fun getUserFavorite(): LiveData<List<UserFavorite>>? {
        return userDao?.getUserFavorite()
    }
}