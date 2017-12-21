package com.rudearts.reposearcher.ui.repo.list

import android.os.Bundle
import com.mindorks.nybus.NYBus
import com.rudearts.reposearcher.domain.FavoritesOperand
import com.rudearts.reposearcher.domain.RepoLoadable
import com.rudearts.reposearcher.domain.SaveFavoritesUseCase
import com.rudearts.reposearcher.model.DetailsEvent
import com.rudearts.reposearcher.model.LoadingState
import com.rudearts.reposearcher.model.local.Repo
import com.rudearts.reposearcher.ui.details.fragment.DetailsFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class RepoListPresenter @Inject constructor(
        private val view:RepoListContract.View,
        private val loader: RepoLoadable,
        private val saver:FavoritesOperand.Save,
        private val updater:FavoritesOperand.Update) : RepoListContract.Presenter {

    private var storedUrl:String? = null

    override fun loadRepos(query: String) {
        view.changeListVisibility(LoadingState.LOADING)

        loader.loadRepos(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({repos -> onReposLoaded(repos)},
                        {error -> onError(error)})
    }

    private fun onError(error: Throwable) {
        error.printStackTrace()
        view.changeListVisibility(LoadingState.NO_RESULTS)
    }

    private fun onReposLoaded(repos: ArrayList<Repo>) {
        when(repos.isEmpty()) {
            true -> view.changeListVisibility(LoadingState.NO_RESULTS)
            false -> view.changeListVisibility(LoadingState.SHOW_RESULTS)
        }

        val updated = updater.update(repos)
        view.updateRepos(updated)

        if (shouldNotifyDetails(repos)) {
            notifyDetailsFragment(chooseDetailUrl(updated[0]))
        }
    }

    private fun chooseDetailUrl(repo:Repo) = when(storedUrl) {
        null -> repo.detailsUrl
        else -> storedUrl
    }

    private fun shouldNotifyDetails(repos: ArrayList<Repo>) = view.isLandscape() and !repos.isEmpty()

    override fun onRepoClick(repo: Repo) {
        when(view.isLandscape()) {
            true -> notifyDetailsFragment(repo.detailsUrl)
            false -> showDetailsActivity(repo)
        }
    }

    private fun showDetailsActivity(repo: Repo) {
        val extras = Bundle().apply {
            putString(DetailsFragment.LINK, repo.detailsUrl)
        }
        view.showDetailsActivity(extras)
    }

    private fun notifyDetailsFragment(detailsUrl: String?) {
        NYBus.get().post(DetailsEvent(detailsUrl))
    }

    override fun saveFavorites(repos: List<Repo>?) {
        repos?.let {
            saver.save(it)
        }
    }

    override fun restoreStoredUrl(detailsUrl: String?) {
        storedUrl = detailsUrl
    }

    override fun retrieveStoredUrl() = storedUrl
}