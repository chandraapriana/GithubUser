package com.chandra.github.ui.activity


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chandra.github.R
import com.chandra.github.data.model.UserResponse
import com.chandra.github.databinding.ActivityUserDetailBinding
import com.chandra.github.ui.adapter.SectionsPagerAdapter
import com.chandra.github.ui.fragment.PreferenceFragment
import com.chandra.github.ui.viewmodel.MainViewModel
import com.chandra.github.ui.viewmodel.UserViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Suppress("DEPRECATION")
class UserDetailActivity : AppCompatActivity() {

    private lateinit var mUserViewModel: UserViewModel
    private var statusFavorite: Boolean = false

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.subtitle_followers,
            R.string.subtitle_following
        )
        val PREVIOUS_ACTIVITY: String = ""
        var EXTRA_USERNAME: String = "-"

    }

    private lateinit var binding: ActivityUserDetailBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val username: String = intent.getStringExtra(EXTRA_USERNAME).toString()

        binding.progressBar.visibility = View.VISIBLE
        viewModel.getUserData(username)

        CheckFavoriteUser(username)
        detailUser()

        val sectionsPagerAdapter =
            SectionsPagerAdapter(this@UserDetailActivity)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tab
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()
        supportActionBar?.elevation = 0f

        binding.toolbarDetail.btnBack.setOnClickListener {
            finish()
        }

        binding.toolbarDetail.btnSetting.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.detail_container, PreferenceFragment())
                .addToBackStack(null).commit()
        }

    }

    private fun CheckFavoriteUser(username: String) {
        val previousActivity: String = intent.getStringExtra(PREVIOUS_ACTIVITY).toString()
        if (previousActivity == "FavoriteFragment") {
            statusFavorite = true
            setStatusFavorite(statusFavorite)
        } else {
            statusFavorite = false
            setStatusFavorite(statusFavorite)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val count = mUserViewModel.getFavoriteUserByUsername(username.toString())
            withContext(Dispatchers.Main) {
                if (count > 0.toString()) {
                    statusFavorite = true
                    setStatusFavorite(statusFavorite)
                } else {
                    statusFavorite = false
                    setStatusFavorite(statusFavorite)
                }
            }
        }
    }

    private fun detailUser() {
        viewModel.userData.observe(this) {
            val data = it
            binding.apply {
                tvNameDetail.text = data.name
                toolbarDetail.tvUsernameDetail.text = data.login
                tvRepositoryDetail.text = data.publicRepos.toString()
                tvCompanyDetail.text = data.company ?: "-"

                tvFollowingDetail.text = data.following.toString()
                tvFollowersDetail.text = data.followers.toString()
                tvLocationDetail.text = data.location ?: "-"

                toolbarDetail.btnShare.setOnClickListener {
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "text/plain"
                    val shareBody =
                        """
                               ${data.name}
                               Location: ${data.location}
                               Company: ${data.company}
                               https://github.com/${data.login}
                               """.trimIndent()
                    sharingIntent.putExtra(
                        Intent.EXTRA_SUBJECT,
                        "Github User"
                    )
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    startActivity(
                        it.context,
                        Intent.createChooser(sharingIntent, "Share via"),
                        null
                    )
                }

                data.avatarUrl.let {
                    Glide.with(applicationContext).load(it).into(civImageDetail)
                        .apply { RequestOptions().override(120, 120) }
                }

                fabAdd.setOnClickListener {
                    if (!statusFavorite) {
                        addUser(data)
                        statusFavorite = true
                        setStatusFavorite(statusFavorite)
                    } else {
                        deleteUser(data)
                        statusFavorite = false
                        setStatusFavorite(statusFavorite)
                    }
                }

                progressBar.visibility = View.INVISIBLE

            }
        }

    }

    private fun addUser(user: UserResponse) {
        mUserViewModel.addUser(user)
        Toast.makeText(applicationContext, "Succesfully added", Toast.LENGTH_LONG).show()
    }

    private fun deleteUser(user: UserResponse) {
        mUserViewModel.deleteUser(user)
        Toast.makeText(applicationContext, "Succesfully delete", Toast.LENGTH_LONG).show()
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabAdd.setImageResource(R.drawable.ic_favorite_fill)

        } else {
            binding.fabAdd.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}


