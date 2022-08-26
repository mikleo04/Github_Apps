package com.example.mygithubappsfix.user_interface.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubappsfix.data.User
import com.example.mygithubappsfix.data.local.UserFavorite
import com.example.mygithubappsfix.databinding.ActivityFavoriteBinding
import com.example.mygithubappsfix.user_interface.detail.UserDetailActivity
import com.example.mygithubappsfix.user_interface.main.ListUserAdapter

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapterList: ListUserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Favorite"

        adapterList = ListUserAdapter()
        adapterList.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapterList.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUser.adapter = adapterList
        }

        viewModel.getUserFavorite()?.observe(this, {
            if (it != null) {
                val list = mappingList(it)
                adapterList.setList(list)
            }
        })

    }

    private fun mappingList(users: List<UserFavorite>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapping = User(
                user.login,
                user.avatar_url,
                user.id
            )
            listUsers.add(userMapping)
        }
        return listUsers

    }
}