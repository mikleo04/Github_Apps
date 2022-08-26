package com.example.mygithubappsfix.user_interface.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mygithubappsfix.data.User
import com.example.mygithubappsfix.databinding.ItemUserBinding

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private val list = ArrayList<User>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    
    fun setList(users: ArrayList<User>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .circleCrop()
                    .into(ivUser)
                tvUsername.text = user.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder((view))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}