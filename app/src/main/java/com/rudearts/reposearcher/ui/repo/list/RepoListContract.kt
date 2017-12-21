package com.rudearts.reposearcher.ui.repo.list

import android.os.Bundle
import com.rudearts.reposearcher.model.LoadingState
import com.rudearts.reposearcher.model.local.Repo

interface RepoListContract {

    interface View {
        fun changeListVisibility(state: LoadingState)
        fun updateRepos(repos:ArrayList<Repo>)
        fun isLandscape():Boolean
        fun showDetailsActivity(extras:Bundle)
    }

    interface Presenter {
        fun restoreStoredUrl(detailsUrl:String?)
        fun loadRepos(query:String)
        fun onRepoClick(repo:Repo)
        fun saveFavorites(repos:List<Repo>?)
        fun retrieveStoredUrl():String?
    }

}