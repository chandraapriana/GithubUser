package com.chandra.github.ui.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.chandra.github.data.model.UserSearchData
import com.chandra.github.ui.adapter.ListAdapter
import com.chandra.github.ui.viewmodel.MainViewModel
import com.chandra.github.databinding.FragmentFollowersBinding
import com.chandra.github.ui.activity.UserDetailActivity

@Suppress("DEPRECATION")
class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var listAdapter: ListAdapter

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        binding.rvFollowers.layoutManager = LinearLayoutManager(context)
        binding.rvFollowers.setHasFixedSize(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        searchListFollowers()
    }


    private fun searchListFollowers() {
        viewModel.listFollowers.observe(viewLifecycleOwner) {
            listAdapter =
                ListAdapter(it)

            binding.rvFollowers.adapter = listAdapter
            listAdapter.setOnItemClickCallback(object : ListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserSearchData) {
                    val intent = Intent(context, UserDetailActivity::class.java)
                    intent.putExtra(UserDetailActivity.EXTRA_USERNAME, data.login)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
            })
            binding.progressBar.visibility = View.INVISIBLE
            if (it.count() == 0) binding.zeroFollowers.visibility = View.VISIBLE

        }

    }


}