package com.chandra.github.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.chandra.github.R
import com.chandra.github.data.database.UserDao
import com.chandra.github.data.database.UserDatabase
import com.chandra.github.ui.viewmodel.UserViewModel


internal class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val favoriteUsers = ArrayList<String>()
    private lateinit var mUserViewModel: UserViewModel
    private var userDao: UserDao? = null
    override fun onCreate() {
        userDao = UserDatabase.getDatabase(context = mContext).userDao()

    }

    override fun onDataSetChanged() {
        val usersFavDB  = userDao?.getWidgetData()
        for (i in 0 until usersFavDB!!.count()  ){
            favoriteUsers.add(usersFavDB[i].avatarUrl)
        }


    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)

        try {
            val bitmap = Glide.with(mContext)
                .asBitmap()
                .load(favoriteUsers[position])
                .submit()
                .get()

            rv.setImageViewBitmap(R.id.imageView, bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val extras = bundleOf(
            ImageBannerWidget.EXTRA_ITEM to position
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return rv

    }

    override fun getCount(): Int =  favoriteUsers.size



    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1
    override fun onDestroy() {
       userDao = null
    }

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false



}