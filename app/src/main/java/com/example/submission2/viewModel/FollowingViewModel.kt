package com.example.submission2.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.AppConstant
import com.example.submission2.model.User
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import java.lang.Exception

class FollowingViewModel: ViewModel() {

    private val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setFollowingUser(username: String) {
        val listItem = ArrayList<User>()

        val apiKey = AppConstant.API_KEY
        val url = "https://api.github.com/users/$username/following"

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
                    listFollowing.postValue(listItem)
                } catch (e: Exception) {
                    Log.d("FollowingViewModel", "On Success: ${e.message.toString()}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("FollowingViewModel", "On Failure: ${error?.message.toString()}")

                val message = when(statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : Check Your Connectivity"
                }

                Log.d("FollowingViewModel", message)
            }
        })
    }

    fun getFollowingUser(): LiveData<ArrayList<User>> {
        return listFollowing
    }
}