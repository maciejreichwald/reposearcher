package com.rudearts.reposearcher.util

import com.rudearts.reposearcher.model.external.RepoExternal
import com.rudearts.reposearcher.model.local.Repo

interface ExternalMappable {

    fun repo2local(track:RepoExternal):Repo

}