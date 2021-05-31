package com.chandra.consumerapp

import android.content.UriMatcher
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chandra.consumerapp.adapter.ListAdapter
import com.chandra.consumerapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {


    companion object {
        const val AUTHORITY = "com.chandra.github"
        const val SCHEME = "content"
        const val TABLE_NAME = "user_table"

        const val LIST = 1
        const val OBJECT = 2
        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()

        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_NAME, LIST)
            uriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", OBJECT)
        }

    }

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvFavorite.layoutManager = LinearLayoutManager(this)

        listAdapter = ListAdapter(listOf())



        val cursor = contentResolver?.query(CONTENT_URI,null,null,null,null)
        val userList =  MappingHelper.mapCursorToArrayList(cursor)


        listAdapter=ListAdapter(userList)
        binding.rvFavorite.adapter = listAdapter


    }


}

