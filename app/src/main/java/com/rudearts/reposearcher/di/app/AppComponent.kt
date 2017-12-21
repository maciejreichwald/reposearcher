package com.rudearts.reposearcher.di.app

import com.rudearts.reposearcher.domain.FavoritesOperand
import com.rudearts.reposearcher.domain.RepoLoadable
import com.rudearts.reposearcher.domain.SaveFavoritesUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(DomainModule::class))
interface AppComponent {
    val loader:RepoLoadable
    val saver:FavoritesOperand.Save
    val updater:FavoritesOperand.Update
}