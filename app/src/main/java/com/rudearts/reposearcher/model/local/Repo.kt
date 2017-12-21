package com.rudearts.reposearcher.model.local

import java.util.*


data class Repo(
        val id:Long,
        val name:String,
        val owner:Owner,
        val description:String?,
        val detailsUrl:String?,
        val forks:Int,
        val favorite:Boolean=false
)