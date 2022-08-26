package com.example.mygithubappsfix.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserFavoriteDao {
    @Insert
    suspend fun addToFavorite(userFavorite: UserFavorite)

    @Query("SELECT * FROM user_favorite")
    fun getUserFavorite():LiveData<List<UserFavorite>>

    @Query("SELECT count(*) FROM user_favorite WHERE user_favorite.id = :id")
    suspend fun checkuser(id: Int): Int

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    suspend fun deleteFromFavorite(id: Int):Int
}