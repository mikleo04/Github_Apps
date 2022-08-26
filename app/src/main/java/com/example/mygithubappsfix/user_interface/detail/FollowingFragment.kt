package com.example.mygithubappsfix.user_interface.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubappsfix.R
import com.example.mygithubappsfix.databinding.FragmentFollowBinding
import com.example.mygithubappsfix.user_interface.main.ListUserAdapter

class FollowingFragment : Fragment(R.layout.fragment_follow) {
    private var bindingFollow : FragmentFollowBinding? = null
    private val binding get() = bindingFollow!!
    private lateinit var viewModel: FollowingViewModel
    private lateinit var adapter: ListUserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        username = args?.getString(UserDetailActivity.EXTRA_USERNAME).toString()

        bindingFollow = FragmentFollowBinding.bind(view)
        adapter = ListUserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollow.setHasFixedSize(true)
            rvFollow.layoutManager = LinearLayoutManager(activity)
            rvFollow.adapter = adapter
        }

        loading(true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        viewModel.setFollowing(username)
        viewModel.getFollowing().observe(viewLifecycleOwner, {
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
        bindingFollow = null
    }

}