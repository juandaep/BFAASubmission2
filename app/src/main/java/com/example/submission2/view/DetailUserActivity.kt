package com.example.submission2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submission2.R
import com.example.submission2.adapter.SectionPagerAdapter
import com.example.submission2.model.User
import com.example.submission2.viewModel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail_user.*

class DetailUserActivity : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private fun setActionBarTitle(username: String) {
        if (supportActionBar != null) {
            (supportActionBar as ActionBar).title = username
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        showLoading(true)
        val userDetail = intent.getParcelableExtra<User>(EXTRA_USER) as User

        setActionBarTitle(userDetail.username)

        Glide.with(this)
                .load(userDetail.avatar)
                .into(img_avatar_detail)
        tv_userName_detail.text = userDetail.username

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        detailViewModel.setDetailUser(userDetail.username)
        detailViewModel.getDetailUser().observe(this, { user ->
            if (user != null) {
                tv_name.text = user.name
                tv_company.text = user.company
                tv_location.text = user.location
                tv_10.text = user.repository.toString()
                tv_15.text = user.followers.toString()
                tv_20.text = user.following.toString()
            }

            showLoading(false)
        })

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        sectionPagerAdapter.setData(userDetail.username)
        view_pager.adapter = sectionPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progress_detail.visibility = View.VISIBLE
        } else {
            progress_detail.visibility = View.GONE
        }
    }
}