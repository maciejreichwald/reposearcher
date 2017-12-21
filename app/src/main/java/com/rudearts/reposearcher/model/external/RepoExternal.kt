package com.rudearts.reposearcher.model.external

import com.google.gson.annotations.SerializedName


data class RepoExternal (
        val id: Long,
        val name:String?,
        val description:String?,
        val owner:OwnerExternal?,
        @SerializedName("html_url") val htmlUrl:String?,
        val forks:Int?
)