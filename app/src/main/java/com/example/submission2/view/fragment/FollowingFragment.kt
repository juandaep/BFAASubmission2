package com.example.submission2.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.R
import com.example.submission2.adapter.FollowingAdapter
import com.example.submission2.viewModel.FollowingViewModel
import kotlinx.android.synthetic.main.fragment_following.*


class FollowingFragment : Fragment() {

    private lateinit var adapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    companion object{
        const val EXTRA_FOLLOWING = "extra_following"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowingAdapter()
        adapter.notifyDataSetChanged()

        showRecyclerView()

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowingViewModel::class.java)
        if (arguments != null) {
            val username = arguments?.getString(FollowingFragment.EXTRA_FOLLOWING)
            followingViewModel.setFollowingUser(username.toString())
        }


        followingViewModel.getFollowingUser().observe(viewLifecycleOwner,  { followers ->
            if (followers.size > 0) {
                adapter.setData(followers)
            } else {
                img_following.visibility = View.VISIBLE
            }
        })
    }

    private fun showRecyclerView() {
        rv_list_following.layoutManager = LinearLayoutManager(context)
        rv_list_following.adapter = adapter
    }
}