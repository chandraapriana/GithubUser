package com.chandra.github.data.database

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.chandra.github.data.model.UserResponse

@Dao
interface UserDao {

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<UserResponse>>

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getWidgetData(): List<UserResponse>

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllDataProvider(): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserResponse)

    @Query("Delete From user_table")
    suspend fun deleteAllUsers()

    @Delete
    suspend fun deleteUser(user: UserResponse)



    @Query("SELECT count(*) FROM user_table WHERE user_table.login = :login")
    suspend fun getUserFavoriteByUsername(login: String): String

}