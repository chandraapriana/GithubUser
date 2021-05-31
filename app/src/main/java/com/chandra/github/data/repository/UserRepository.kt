package com.chandra.github.data.repository

import androidx.lifecycle.LiveData
import com.chandra.github.data.database.UserDao
import com.chandra.github.data.model.UserResponse

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<UserResponse>> = userDao.readAllData()
    suspend fun addUser(user: UserResponse) {
        userDao.addUser(user)
    }

    suspend fun deleteAllUser() {
        userDao.deleteAllUsers()
    }

    suspend fun deleteUser(user: UserResponse) {
        userDao.deleteUser(user)
    }

    suspend fun getUserFavoriteByUsername(username: String) = userDao.getUserFavoriteByUsername(username)
}