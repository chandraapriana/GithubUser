package com.chandra.github.ui.activity


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.chandra.github.R
import com.chandra.github.data.model.UserSearchData
import com.chandra.github.databinding.ActivityMainBinding
import com.chandra.github.ui.adapter.ListAdapter
import com.chandra.github.ui.fragment.FavoriteFragment
import com.chandra.github.ui.fragment.PreferenceFragment
import com.chandra.github.ui.viewmodel.MainViewModel

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var listAdapter: ListAdapter
   private val viewModel:MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvUsers.layoutManager = LinearLayoutManager(this)

        binding.toolbarMain.btnSetting.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.main_container, PreferenceFragment())
                .addToBackStack(null).commit()
        }

        binding.toolbarMain.btnFavorite.setOnClickListener {

            val fragmentTranscation: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTranscation.replace(R.id.main_container, FavoriteFragment())
                .addToBackStack(null).commit()
        }

        binding.svMain.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("main", newText)
                if (newText.isNotEmpty()) searchUser(newText)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                searchUser(query.toString())
                return true
            }
        })
    }


    private fun searchUser(username: String) {
        binding.progressBar.visibility = View.VISIBLE
        Log.d("main", username)

        viewModel.getUserSearch(username)
        viewModel.userSearch.observe(this) {

            if (it != null) {
                listAdapter = ListAdapter(
                    it.items
                )
                binding.rvUsers.adapter = listAdapter
                listAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: UserSearchData) {
                        val intent = Intent(baseContext, UserDetailActivity::class.java)
                        intent.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    }
                })


            }else{
                listAdapter = ListAdapter(
                    emptyList()
                )
                binding.rvUsers.adapter = listAdapter
            }

            binding.progressBar.visibility = View.GONE
        }
    }

}