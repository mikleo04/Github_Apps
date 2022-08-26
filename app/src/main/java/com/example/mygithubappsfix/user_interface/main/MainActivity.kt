package com.example.mygithubappsfix.user_interface.main

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubappsfix.R
import com.example.mygithubappsfix.data.User
import com.example.mygithubappsfix.data.local.LocalShared
import com.example.mygithubappsfix.databinding.ActivityMainBinding
import com.example.mygithubappsfix.user_interface.detail.UserDetailActivity
import com.example.mygithubappsfix.user_interface.favorite.FavoriteActivity
import com.example.mygithubappsfix.user_interface.menu.MenuFragment
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapterList: ListUserAdapter
    var isCurrentThemeDarkMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        getCurrentTheme()
        setAppTheme(isCurrentThemeDarkMode)
        setContentView(binding.root)

        adapterList = ListUserAdapter()
        adapterList.notifyDataSetChanged()
        adapterList.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(UserDetailActivity.EXTRA_ID, data.id)
                    it.putExtra(UserDetailActivity.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserViewModel::class.java)

        binding.apply {
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                rvUser.layoutManager = GridLayoutManager(this@MainActivity, 2)
            } else {
                rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            }
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapterList

            btnSearch.setOnClickListener {
               searchUser()
            }

            etQuery.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    searchUser()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        viewModel.getSearchUser().observe(this, {
            if (it != null) {
                adapterList.setList(it)
                loading(false)
            }
        })
    }

    private fun setAppTheme(isDarkMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(if (isDarkMode) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, MenuFragment())
                    .addToBackStack(null)
                    .commit()
                return true
            }

            R.id.menu2 -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
                return true
            }

            R.id.action_theme -> {
                isCurrentThemeDarkMode = !isCurrentThemeDarkMode
                putDataTheme(isCurrentThemeDarkMode)
                return true
            }
            else -> return true
        }
    }

    private fun putDataTheme(isDarkMode: Boolean) {
        lifecycleScope.launch { LocalShared.updateThemes(this@MainActivity, isDarkMode) }
    }

    private fun getCurrentTheme() {
        lifecycleScope.launch {
            LocalShared.getThemes(this@MainActivity).collect {
                isCurrentThemeDarkMode = it
                setAppTheme(it)
            }
        }
    }

    private fun loading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun searchUser() {
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty()) return
            loading(true)
            viewModel.setSearchUser(query)
        }
    }


}