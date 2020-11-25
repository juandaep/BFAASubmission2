package com.example.submission2.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.R
import com.example.submission2.adapter.FollowersAdapter
import com.example.submission2.viewModel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    private lateinit var adapter: FollowersAdapter
    private lateinit var followersViewModel: FollowersViewModel

    companion object{
        const val EXTRA_FOLLOWERS = "extra_followers"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowersAdapter()
        adapter.notifyDataSetChanged()

        showRecyclerView()

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        if (arguments != null) {
            val username = arguments?.getString(EXTRA_FOLLOWERS)
            followersViewModel.setFollowersUser(username.toString())
        }

        followersViewModel.getFollowersUser().observe(viewLifecycleOwner, { followers ->
            if (followers.size > 0) {
                adapter.setData(followers)
            } else {
                img_follow.visibility = View.VISIBLE
            }
        })
    }

    private fun showRecyclerView() {
        rv_list_followers.layoutManager = LinearLayoutManager(context)
        rv_list_followers.adapter = adapter
    }
}