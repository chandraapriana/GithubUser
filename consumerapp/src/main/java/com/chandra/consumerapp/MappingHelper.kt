package com.chandra.consumerapp

import android.database.Cursor
import com.chandra.github.data.model.UserResponse
import java.util.ArrayList

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<UserResponse> {
        val userList = ArrayList<UserResponse>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val avatarUrl = getString(getColumnIndexOrThrow("avatarUrl"))
                val htmlUrl = getString(getColumnIndexOrThrow("htmlUrl"))
                val company = getString(getColumnIndexOrThrow("company"))
                val followers = getInt(getColumnIndexOrThrow("followers"))
                val following = getInt(getColumnIndexOrThrow("following"))
                val login = getString(getColumnIndexOrThrow("login"))
                val location = getString(getColumnIndexOrThrow("location"))
                val name = getString(getColumnIndexOrThrow("name"))
                val publicRepos = getInt(getColumnIndexOrThrow("publicRepos"))
                userList.add(UserResponse(id, avatarUrl,htmlUrl, company, followers,following,location,login,name,publicRepos))
            }
        }
        return userList
    }
}