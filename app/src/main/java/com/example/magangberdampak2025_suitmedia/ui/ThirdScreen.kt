package com.example.magangberdampak2025_suitmedia.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.magangberdampak2025_suitmedia.R
import com.example.magangberdampak2025_suitmedia.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class ThirdScreen : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private val userViewModel: UserViewModel by viewModels()
    private lateinit var swipeRefresh: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_screen)

        val tbThirdScreen = findViewById<Toolbar>(R.id.tbThirdScreen)
        recyclerView = findViewById(R.id.rvUsers)
        val pbInitial = findViewById<ProgressBar>(R.id.pbInitial)
        val pbPaging = findViewById<ProgressBar>(R.id.pbPaging)
        swipeRefresh = findViewById(R.id.swiperefresh)

        swipeRefresh.setOnRefreshListener {
            userViewModel.reset()
            userViewModel.fetchUsers()
        }

        userViewModel.fetchUsers()


        setSupportActionBar(tbThirdScreen)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tbThirdScreen.setNavigationOnClickListener {
            finish()
        }

//        val users = listOf(
//            User(1, "john@example.com", "John", "Doe", "https://reqres.in/img/faces/1-image.jpg"),
//            User(2, "jane@example.com", "Jane", "Smith", "https://reqres.in/img/faces/2-image.jpg"),
//            User(3, "bob@example.com", "Bob", "Brown", "https://reqres.in/img/faces/3-image.jpg")
//        )

        userAdapter = UserAdapter(
            userList = emptyList(),
            onItemClick = { user ->
                val fullName = "${user.firstName} ${user.lastName}"
                val intent = Intent()
                intent.putExtra("SELECTED_NAME", fullName)
                setResult(Activity.RESULT_OK, intent)
                finish()

//                val intent = Intent(this, SecondScreen::class.java)
//                intent.putExtra("SELECTED_NAME", fullName)
//                startActivity(intent)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        val visibleThreshold = 1

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                val shouldLoadMore = totalItemCount <= lastVisibleItem + visibleThreshold

                if (shouldLoadMore &&
                    !userViewModel.isPagingLoading.value &&
                    !userViewModel.isInitialLoading.value) {
                    userViewModel.fetchUsers()
                }
            }
        })

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    userViewModel.users.collect { userList ->
                        userAdapter.setData(userList)
                    }
                }
                launch {
                    userViewModel.isInitialLoading.collect { loading ->
                        // Tampilkan atau sembunyikan ProgressBar
                        pbInitial.visibility =
                            if (loading) View.VISIBLE else View.GONE
                    }
                }
                launch {
                    userViewModel.isInitialLoading.collect { loading ->
                        swipeRefresh.isRefreshing = loading
                    }
                }
                launch {
                    userViewModel.isPagingLoading.collect { loading ->
                        // Tampilkan atau sembunyikan ProgressBar
                        pbPaging.visibility =
                            if (loading) View.VISIBLE else View.GONE
                    }
                }
                launch {
                    userViewModel.isError.collect { error ->
                        if (error != null) {
                            Toast.makeText(
                                this@ThirdScreen,
                                error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}