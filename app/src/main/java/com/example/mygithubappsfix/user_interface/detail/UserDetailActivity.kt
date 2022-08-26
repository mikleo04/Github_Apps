package com.example.mygithubappsfix.user_interface.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mygithubappsfix.databinding.ActivityUserDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

        viewModel.setUserDetail(username!!)
        viewModel.getUserDetail().observe(this, {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvBio.text = it.bio
                    tvCompany.text = it.company
                    tvEmail.text = it.email
                    tvLocation.text = it.location
                    tvRepositories.text = "${it.public_repos} Repositories"
                    tvFollowers.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@UserDetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .circleCrop()
                        .into(ivAvatar)

                    tvLocation.setOnClickListener {
                        val getMapsIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr=$tvLocation"))
                        startActivity(getMapsIntent)
                    }
                }
            }
        })

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count != null) {
                    if (count > 0) {
                        binding.buttonFavorite.isChecked = true
                        _isChecked = true
                    } else {
                        binding.buttonFavorite.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.buttonFavorite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.addFavorite(username, id, avatarUrl!!)
            } else {
                viewModel.removeFavorite(id)
            }
            binding.buttonFavorite.isChecked = _isChecked
        }

        val tabsPagerAdapter = TabsPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = tabsPagerAdapter
            tabsFollow.setupWithViewPager(viewPager)
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
    }

}