package com.rudearts.reposearcher.di.app

import android.content.Context
import com.rudearts.reposearcher.api.RestController
import com.rudearts.reposearcher.domain.*
import com.rudearts.reposearcher.util.ExternalMappable
import com.rudearts.reposearcher.util.ExternalMapper
import com.rudearts.reposearcher.util.Preferencable
import com.rudearts.reposearcher.util.Preferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule(private val context:Context) {


    @Provides
    @Singleton
    fun providesPreferences():Preferencable = Preferences(context)

    @Provides
    fun provideExternalMapper():ExternalMappable = ExternalMapper(context)

    @Provides
    @Singleton
    fun provideRestController():RestController = RestController()

    @Provides
    fun providesFavoritesSaver(preferences:Preferencable):FavoritesOperand.Save = SaveFavoritesUseCase(preferences)

    @Provides
    fun providesFavoritesUpdater(preferences:Preferencable):FavoritesOperand.Update = UpdateFavotitesUseCase(preferences)

    @Provides
    fun providesLoadReposUseCase(
            restController: RestController,
            mapper:ExternalMappable):RepoLoadable = LoadReposUseCase(restController, mapper)


}