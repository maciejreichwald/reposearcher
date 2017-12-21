package com.rudearts.reposearcher.model.external

import com.google.gson.annotations.SerializedName

data class OwnerExternal(
        val login:String?,
        @SerializedName("avatar_url") val avatar:String?
)