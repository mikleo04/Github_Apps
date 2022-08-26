package com.example.mygithubappsfix.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetailResponse(
    val login: String,
    val id: Int,
    val name: String,
    val avatar_url: String,
    val bio: String,
    val company: String,
    val email: String,
    val public_repos: Int,
    val location: String,
    val followers: Int,
    val following: Int,
    val followers_url: String,
    val following_url: String
) : Parcelable
