package com.example.submission2.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.submission2.R
import com.example.submission2.view.fragment.FollowersFragment
import com.example.submission2.view.fragment.FollowingFragment

class SectionPagerAdapter(private val context: Context, fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var username: String? ="username"

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.followers, R.string.following)

    fun setData(newUsername: String?) {
        username = newUsername
    }

    private fun getData(): String? {
        return username
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FollowersFragment()
                val bundle = Bundle()
                bundle.putString(FollowersFragment.EXTRA_FOLLOWERS, getData())
                fragment.arguments = bundle
            }

            1 -> {
                fragment = FollowingFragment()
                val bundle = Bundle()
                bundle.putString(FollowingFragment.EXTRA_FOLLOWING, getData())
                fragment.arguments = bundle
            }
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int = 2
}