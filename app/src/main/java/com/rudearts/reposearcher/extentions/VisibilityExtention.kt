package com.rudearts.reposearcher.extentions

import android.view.View

var View.visible : Boolean
    set(value) = when(value) {
        true -> visibility = View.VISIBLE
        false -> visibility = View.GONE
    }
    get() = when(visibility) {
        View.VISIBLE -> true
        View.GONE -> false
        else -> false
    }
