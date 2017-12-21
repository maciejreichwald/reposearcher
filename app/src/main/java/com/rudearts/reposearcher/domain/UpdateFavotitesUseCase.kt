package com.rudearts.reposearcher.domain

import com.google.gson.Gson
import com.rudearts.reposearcher.model.external.FavoriteList
import com.rudearts.reposearcher.model.local.Repo
import com.rudearts.reposearcher.util.Preferencable
import javax.inject.Inject

class UpdateFavotitesUseCase @Inject constructor(private val preferences:Preferencable) : FavoritesOperand.Update {

    override fun update(repos: ArrayList<Repo>):ArrayList<Repo> {
        val json = preferences.favorites
        val favorites = Gson().fromJson<FavoriteList>(json, FavoriteList::class.java)

        return when(favorites) {
            null -> repos
            else -> ArrayList(repos.map { updateFavorite(it, favorites) })
        }
    }

    private fun updateFavorite(repo: Repo, favorites: FavoriteList) = when(containsFavorite(repo, favorites)) {
        true -> repo.copy(favorite = true)
        false -> repo
    }

    private fun containsFavorite(repo: Repo, favorites: FavoriteList) = favorites.contains(repo.id)
}