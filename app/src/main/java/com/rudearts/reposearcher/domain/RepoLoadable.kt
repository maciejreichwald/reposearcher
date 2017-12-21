package com.rudearts.reposearcher.domain

import com.rudearts.reposearcher.model.local.Repo
import io.reactivex.Single

interface RepoLoadable {

    fun loadRepos(username:String):Single<ArrayList<Repo>>

}