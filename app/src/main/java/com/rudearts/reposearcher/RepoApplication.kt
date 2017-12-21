package com.rudearts.reposearcher

import android.app.Application
import com.rudearts.reposearcher.di.app.AppComponent
import com.rudearts.reposearcher.di.app.DaggerAppComponent
import com.rudearts.reposearcher.di.app.DomainModule

class RepoApplication : Application() {

    companion object{
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        createComponent()
    }

    internal fun createComponent() {
        appComponent =  DaggerAppComponent.builder()
                .domainModule(DomainModule(this))
                .build()
    }
}