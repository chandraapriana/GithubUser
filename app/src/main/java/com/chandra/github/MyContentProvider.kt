package com.chandra.github

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.chandra.github.data.database.UserDao
import com.chandra.github.data.database.UserDatabase

class MyContentProvider : ContentProvider() {

    private lateinit var userDao: UserDao

    companion object {
        const val AUTHORITY = "com.chandra.github"
        const val SCHEME = "content"
        const val TABLE_NAME = "user_table"

        const val LIST = 1
        const val OBJECT = 2

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, LIST)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", OBJECT)
        }
    }


    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null

    }

    override fun onCreate(): Boolean {
        userDao = Room.databaseBuilder(
            context!!, UserDatabase::class.java, "user_database"
        ).fallbackToDestructiveMigration().build().userDao()
        return true
    }


    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            LIST -> {
                userDao.readAllDataProvider()
            }
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}