package com.chandra.github.data.model
import com.google.gson.annotations.SerializedName

class UserSearchData(
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("html_url")
    val htmlUrl: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("login")
    val login: String?

)