package com.rudearts.reposearcher.domain

import com.google.gson.Gson
import com.rudearts.reposearcher.model.external.FavoriteList
import com.rudearts.reposearcher.model.local.Repo
import com.rudearts.reposearcher.util.Preferencable
import javax.inject.Inject

class SaveFavoritesUseCase @Inject constructor(private val preferences:Preferencable) : FavoritesOperand.Save {

    override fun save(repos: List<Repo>) {
        val favorites = repos.map { it.id }

        val json = Gson().toJson(favorites, FavoriteList::class.java)
        preferences.favorites = json
    }
}