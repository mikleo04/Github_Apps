package com.example.mygithubappsfix.user_interface.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubappsfix.R
import com.example.mygithubappsfix.databinding.FragmentFollowBinding
import com.example.mygithubappsfix.user_interface.main.ListUserAdapter

class FollowersFragment : Fragment(R.layout.fragment_follow) {
    private var binding_follow : FragmentFollowBinding? = null
    private val binding get() = binding_follow!!
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(UserDetailActivity.EXTRA_USERNAME).toString()

        binding_follow = FragmentFollowBinding.bind(view)
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollow.setHasFixedSize(true)
            rvFollow.layoutManager = LinearLayoutManager(activity)
            rvFollow.adapter = adapter
        }

        loading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModel.setFollowers(username)
        viewModel.getFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                loading(false)
            }
        })
    }

    private fun loading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding_follow = null
    }

}