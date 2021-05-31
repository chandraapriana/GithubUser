package com.chandra.github.ui

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
                val htmlUrl = getString(getColumnIndexOrThrow("html_url"))
                val company = getString(getColumnIndexOrThrow("company"))
                val followers = getInt(getColumnIndexOrThrow("followers"))
                val following = getInt(getColumnIndexOrThrow("following"))
                val location = getString(getColumnIndexOrThrow("location"))
                val login = getString(getColumnIndexOrThrow("login"))
                val name = getString(getColumnIndexOrThrow("name"))
                val publicRepos = getInt(getColumnIndexOrThrow("public_repos"))
                userList.add(UserResponse(id, avatarUrl,htmlUrl, company, followers,following,location,login,name,publicRepos))
            }
        }
        return userList
    }
}