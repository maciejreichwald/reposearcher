package com.rudearts.reposearcher.ui.repo

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.ViewGroup
import com.mindorks.nybus.thread.NYThread
import com.rudearts.reposearcher.R
import com.rudearts.reposearcher.extentions.bind
import com.rudearts.reposearcher.extentions.visible
import com.rudearts.reposearcher.model.LoadingState
import com.rudearts.reposearcher.ui.ToolbarActivity
import com.rudearts.reposearcher.ui.details.fragment.DetailsFragment
import com.rudearts.reposearcher.ui.repo.list.RepoListFragment

class RepoActivity : ToolbarActivity() {

    internal val details:ViewGroup by bind(R.id.details_container)

    private var detailsFragment:DetailsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    internal fun setup() {
        setupTitle()
        setupListFragment()
        setupDetailsFragment()
    }

    private fun setupDetailsFragment() {
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        details.visible = isLandscape

        if (!isLandscape) return

        detailsFragment = DetailsFragment()
        detailsFragment?.let {
            it.arguments = Bundle().apply {
                putInt(DetailsFragment.START_STATE, LoadingState.LOADING.ordinal)
            }
            addFragment(it, R.id.details_container)
        }
    }

    private fun setupListFragment() {
        val fragment = RepoListFragment()
        fragment.arguments = intent.extras
        addFragment(fragment, R.id.list_container)
    }

    private fun addFragment(fragment: Fragment, containerId:Int) {
        supportFragmentManager.beginTransaction()
                .add(containerId, fragment)
                .commit()
    }

    override fun provideSubContentViewId() = R.layout.activity_repo

    internal fun setupTitle() {
        setTitle(R.string.repo_list)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onBackPressed() = when(hasLoadedWebView()) {
        true -> {
            detailsFragment?.goBack()
            Unit
        }
        false -> super.onBackPressed()
    }

    private fun hasLoadedWebView() = detailsFragment?.canGoBack() ?: false
}
