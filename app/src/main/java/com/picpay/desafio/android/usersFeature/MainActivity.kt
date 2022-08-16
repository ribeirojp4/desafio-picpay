package com.picpay.desafio.android.usersFeature

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.picpay.desafio.android.R
import com.picpay.desafio.android.ViewState
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.gone
import com.picpay.desafio.android.usersFeature.adapters.UserListAdapter
import com.picpay.desafio.android.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var binding: ActivityMainBinding

    private val usersViewModel: UsersViewModel by viewModel()

    private val userListAdapter = UserListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews(binding)
        getUsersVO(forceFetch = false)
    }

    private fun setupViews(binding: ActivityMainBinding) {
        with(binding) {
            progressBar = userListProgressBar
            recyclerView = usersRecyclerView.apply {
                adapter = userListAdapter
                layoutManager = LinearLayoutManager(context)
            }
            root.setOnRefreshListener(this@MainActivity)
        }
    }

    private fun getUsersVO(forceFetch: Boolean) {
        usersViewModel.users(forceFetch = forceFetch).apply {
            observe(this@MainActivity) { viewState ->
                binding.root.isRefreshing = false
                when (viewState) {
                    is ViewState.ViewLoadingState -> progressBar.visible()
                    is ViewState.ViewSuccessState -> {
                        progressBar.gone()
                        userListAdapter.usersVO = viewState.data
                    }
                    is ViewState.ViewErrorState -> {
                        val message = getString(R.string.error)

                        progressBar.gone()
                        recyclerView.gone()

                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onRefresh() {
        getUsersVO(forceFetch = true)
    }
}
