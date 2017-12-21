package com.rudearts.reposearcher.util

import android.content.Context
import android.content.ContextWrapper
import com.rudearts.reposearcher.R
import com.rudearts.reposearcher.model.external.OwnerExternal
import com.rudearts.reposearcher.model.external.RepoExternal
import com.rudearts.reposearcher.model.local.Owner
import com.rudearts.reposearcher.model.local.Repo

class ExternalMapper constructor(base:Context) : ContextWrapper(base), ExternalMappable {

    override fun repo2local(track:RepoExternal) = with(track) {
        Repo(id, text2unknown(name), owner2local(owner), description, htmlUrl, number2nonNull(forks))
    }

    fun owner2local(owner:OwnerExternal?)= Owner(text2unknown(owner?.login), owner?.avatar)

    internal fun text2unknown(text:String?) = text ?: getString(R.string.unknown)

    inline fun number2nonNull(forks: Int?) = forks ?: 0

}