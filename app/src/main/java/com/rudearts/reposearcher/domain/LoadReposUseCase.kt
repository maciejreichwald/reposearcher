package com.rudearts.reposearcher.domain

import com.rudearts.reposearcher.api.RestController
import com.rudearts.reposearcher.model.external.RepoExternal
import com.rudearts.reposearcher.model.external.response.SearchResponse
import com.rudearts.reposearcher.model.local.Repo
import com.rudearts.reposearcher.util.ExternalMappable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import retrofit2.Response
import javax.inject.Inject

class LoadReposUseCase @Inject constructor(
        private val restController:RestController,
        private val mapper:ExternalMappable) : RepoLoadable  {

    override fun loadRepos(username: String):Single<ArrayList<Repo>> = Single.create({ subscriber ->
        restController.provideRestApi().search(username)
                .subscribe(
                        {response -> onSearchResposne(response, subscriber)},
                        {error -> onError(error, subscriber)})
    })

    private fun onError(error: Throwable, subscriber: SingleEmitter<ArrayList<Repo>>) {
        subscriber.onError(error)
    }

    internal fun onSearchResposne(response: Response<SearchResponse>, subscriber: SingleEmitter<ArrayList<Repo>>) {
        if (!response.isSuccessful) {
            subscriber.onSuccess(ArrayList())
            return
        }

        val items = response.body() ?: emptyList<RepoExternal>()
        val repos = ArrayList(items.map { mapper.repo2local(it) })
        subscriber.onSuccess(repos)
    }
}