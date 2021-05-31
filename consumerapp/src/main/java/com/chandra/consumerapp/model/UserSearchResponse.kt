package com.chandra.github.data.model

import com.google.gson.annotations.SerializedName

class UserSearchResponse (

    @SerializedName("items")
    val items: List<UserSearchData> = emptyList(),

    @SerializedName("total_count")
    val totalCount: Int = 0

)