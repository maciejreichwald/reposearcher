package com.rudearts.reposearcher.di.main

import com.rudearts.reposearcher.di.ActivityScope
import com.rudearts.reposearcher.domain.FavoritesOperand
import com.rudearts.reposearcher.domain.RepoLoadable
import com.rudearts.reposearcher.ui.repo.list.RepoListContract
import com.rudearts.reposearcher.ui.repo.list.RepoListPresenter
import dagger.Module
import dagger.Provides


@Module
class RepoListModule(private val view:RepoListContract.View) {

    @Provides
    @ActivityScope
    fun providesMainView() = view

    @Provides
    @ActivityScope
    fun providesListPresenter(
            view:RepoListContract.View,
            loader:RepoLoadable,
            saver:FavoritesOperand.Save,
            updater:FavoritesOperand.Update):RepoListContract.Presenter = RepoListPresenter(view,loader, saver, updater)

}