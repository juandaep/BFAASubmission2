package com.example.submission2.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.R
import com.example.submission2.adapter.UserAdapter
import com.example.submission2.model.User
import com.example.submission2.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splash_screen.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        showRecyclerList()

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        mainViewModel.getUsers().observe(this, { userItems ->
            if (userItems.size > 0) {
                adapter.setData(userItems)
                showLoading(false)
            } else {
                Toast.makeText(this, R.string.username_not_found, Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        })
    }

    private fun showRecyclerList() {
        rv_list_user.layoutManager = LinearLayoutManager(this)
        rv_list_user.adapter = adapter

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(user: User) {
        val toDetail = Intent(this, DetailUserActivity::class.java)
        toDetail.putExtra(DetailUserActivity.EXTRA_USER, user)
        startActivity(toDetail)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_button)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.hint_search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                getDataUserFromApi(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.localization -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            else -> return false
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDataUserFromApi(username: String) {
        if (username.isEmpty()) return
        showLoading(true)
        mainViewModel.setUsers(username)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progress_circular.visibility = View.VISIBLE
        } else {
            progress_circular.visibility = View.GONE
        }
    }
}