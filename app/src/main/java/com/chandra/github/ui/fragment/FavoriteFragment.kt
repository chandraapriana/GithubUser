package com.chandra.github.ui.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chandra.github.R
import com.chandra.github.data.model.UserSearchData
import com.chandra.github.databinding.FragmentFavoriteBinding
import com.chandra.github.ui.activity.UserDetailActivity
import com.chandra.github.ui.adapter.ListAdapter
import com.chandra.github.ui.viewmodel.UserViewModel


class FavoriteFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var listAdapter: ListAdapter
    private lateinit var mUserViewModel: UserViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        binding.rvFavorite.layoutManager = LinearLayoutManager(context)
        binding.rvFavorite.setHasFixedSize(true)

        binding.toolbarFavorite.btnBack.setOnClickListener(this)
        binding.toolbarFavorite.btnDeleteAll.setOnClickListener(this)
        binding.toolbarFavorite.btnSetting.setOnClickListener(this)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(viewLifecycleOwner, Observer { user ->
            val userList: ArrayList<UserSearchData> = ArrayList()
            for (i in 0 until user.count()) {
                val userData = UserSearchData(
                    user[i].avatarUrl,
                    "https://github.com/${user[i].login}",
                    user[i].id,
                    user[i].login
                )
                userList.add(userData)
            }
            if (user.count() == 0){
                binding.zeroFavorite.visibility = View.VISIBLE
            }else{
                binding.zeroFavorite.visibility = View.INVISIBLE
            }
            listAdapter = ListAdapter(userList)
            listAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserSearchData) {
                    val intent = Intent(context, UserDetailActivity::class.java)
                    intent.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                    intent.putExtra(UserDetailActivity.PREVIOUS_ACTIVITY, "FavoriteFragment")
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            })

            binding.rvFavorite.adapter = listAdapter
        })

        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onClick(view: View?) {
        when (view?.id) {
            binding.toolbarFavorite.btnBack.id -> fragmentManager?.popBackStack()
            binding.toolbarFavorite.btnDeleteAll.id -> deleteAllUsers()
            binding.toolbarFavorite.btnSetting.id -> {
                val fragmentTransaction: FragmentTransaction =
                    activity?.supportFragmentManager!!.beginTransaction()
                fragmentTransaction.replace(R.id.main_container, PreferenceFragment())
                    .addToBackStack(null).commit()
            }
        }

    }

    private fun deleteAllUsers() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteAllUsers()
            Toast.makeText(
                requireContext(), "Succesfully Remove EveryThing",
                Toast.LENGTH_LONG
            ).show()

        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete Everything")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }


}