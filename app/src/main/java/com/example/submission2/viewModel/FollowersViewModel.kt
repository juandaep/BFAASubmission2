package com.example.submission2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.BuildConfig
import com.example.submission2.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowersViewModel: ViewModel() {

    private val lisFollowers =MutableLiveData<ArrayList<User>>()

    fun setFollowersUser(username: String) {
        val listItem = ArrayList<User>()

        val apiKey = BuildConfig.API_KEY
        val url = "https://api.github.com/users/$username/followers"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $apiKey")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseArray = JSONArray(result)
                    for (i in 0 until responseArray.length()) {
                        val responseObject = responseArray.getJSONObject(i)
                        val user = User()
                        user.avatar = responseObject.getString("avatar_url")
                        user.username = responseObject.getString("login")
                        user.url = responseObject.getString("html_url")
                        listItem.add(user)
                    }
                    lisFollowers.postValue(listItem)
                } catch (e: Exception) {
                    Log.d("FollowersViewModel", "On Success: ${e.message.toString()}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("FollowersViewModel", "On Failure: ${error?.message.toString()}")

                val message = when(statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : Check Your Connectivity"
                }

                Log.d("FollowersViewModel", message)
            }
        })
    }

    fun getFollowersUser(): LiveData<ArrayList<User>> {
        return lisFollowers
    }
}