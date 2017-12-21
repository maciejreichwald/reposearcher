package com.rudearts.reposearcher.domain

import com.rudearts.reposearcher.model.local.Repo

interface FavoritesOperand {

    interface Save {
        fun save(repos:List<Repo>)
    }

    interface Update {
        fun update(repos:ArrayList<Repo>):ArrayList<Repo>
    }

}