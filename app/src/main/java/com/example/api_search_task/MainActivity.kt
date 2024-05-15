package com.example.api_search_task

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.api_search_task.API.UserResponse
import com.example.api_search_task.API.UserResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var recycle: RecyclerView
    lateinit var adapter: CustomAdapter
    lateinit var loading: ProgressBar
    lateinit var edit: EditText
    lateinit var bt1: Button
    lateinit var id: TextView
    lateinit var title: TextView
    lateinit var body: TextView
    lateinit var liner: LinearLayout
    lateinit var scroll: ScrollView
    lateinit var nodata: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycle = findViewById(R.id.rec)
        loading = findViewById(R.id.loading)
        edit = findViewById(R.id.edit)
        bt1 = findViewById(R.id.bt1)
        id = findViewById(R.id.id)
        title = findViewById(R.id.title)
        body = findViewById(R.id.body)
        liner = findViewById(R.id.liner)
        scroll = findViewById(R.id.scroll)
        nodata = findViewById(R.id.nodata)
        liner.visibility = View.GONE

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.INTERNET),
                1
            )
        } else {
            val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo

            if (networkInfo != null && networkInfo.isConnected) {
                // Internet connection is available
                Toast.makeText(this@MainActivity, "Network is Connected", Toast.LENGTH_LONG).show()

            } else {
                // Internet connection is not available
                Toast.makeText(this@MainActivity, "Network is Not Connected", Toast.LENGTH_LONG)
                    .show()
            }
        }

        RetrofitClient().getRetrofitClient().getUsers().enqueue(object : Callback<UserResponse> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    adapter = CustomAdapter(responseBody!!)
                    recycle.layoutManager =
                        LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
                    recycle.adapter = adapter
                    loading.visibility = View.GONE
                    adapter.notifyDataSetChanged()
                } else {
                    loading.isVisible = true
                }
            }
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_LONG).show()
            }
        }
        )


        bt1.setOnClickListener {
            val username = edit.text.toString()
            if (username.isEmpty()) {
                Toast.makeText(this@MainActivity, "Error username", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            RetrofitClient().getRetrofitClient().getSpecificId(username)
                .enqueue(object : Callback<UserResponse> {
                    override fun onResponse(
                        call: Call<UserResponse>,
                        response: Response<UserResponse>
                    ) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            if (responseBody?.size == 0) {
                                scroll.visibility = View.GONE
                                nodata.isVisible = true
                            } else if (responseBody?.size!! > 0) {
                                adapter = CustomAdapter(responseBody!!)
                                recycle.layoutManager =
                                    LinearLayoutManager(
                                        this@MainActivity,
                                        RecyclerView.VERTICAL,
                                        false
                                    )
                                adapter.notifyDataSetChanged()
                                nodata.isVisible = false
                                scroll.isVisible = true
                                recycle.adapter = adapter
                                loading.visibility = View.GONE
                            }
                        } else {
                            loading.isVisible = true
                        }
                    }
                    override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "failed", Toast.LENGTH_LONG).show()
                    }
                }
                )
        }
    }
}