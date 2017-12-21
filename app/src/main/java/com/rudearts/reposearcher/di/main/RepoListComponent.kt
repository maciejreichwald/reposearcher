package com.rudearts.reposearcher.di.main

import com.rudearts.reposearcher.di.ActivityScope
import com.rudearts.reposearcher.di.app.AppComponent
import com.rudearts.reposearcher.ui.repo.list.RepoListFragment
import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(RepoListModule::class))
interface RepoListComponent {
    fun inject(view:RepoListFragment)
}