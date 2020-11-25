package com.example.submission2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2.R
import com.example.submission2.model.User
import kotlinx.android.synthetic.main.item_follow.view.*

class FollowingAdapter: RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {

    private val listFollowingUser = ArrayList<User>()

    fun setData(item: ArrayList<User>) {
        listFollowingUser.clear()
        listFollowingUser.addAll(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): FollowingViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_follow, viewGroup, false)
        return FollowingViewHolder(mView)
    }

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        holder.bind(listFollowingUser[position])
    }

    override fun getItemCount(): Int = listFollowingUser.size

    inner class FollowingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(this)
                    .load(user.avatar)
                    .placeholder(R.drawable.github)
                    .error(R.drawable.github)
                    .into(img_avatar_follow)
                tv_userName_follow.text = user.username
                tv_link_follow.text = user.url
            }
        }

    }
}