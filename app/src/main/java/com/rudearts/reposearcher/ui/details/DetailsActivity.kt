package com.rudearts.reposearcher.ui.details

import android.app.Fragment
import android.os.Bundle
import android.os.PersistableBundle
import com.rudearts.reposearcher.R
import com.rudearts.reposearcher.ui.ToolbarActivity
import com.rudearts.reposearcher.ui.details.fragment.DetailsFragment

class DetailsActivity : ToolbarActivity() {

    private lateinit var detailsFragment:DetailsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setup()
    }

    private fun setup() {
        setupTitle()
        setupFragment()
    }

    private fun setupFragment() {
        detailsFragment =  DetailsFragment().apply {
            arguments = intent.extras

        }
        supportFragmentManager.beginTransaction()
                .add(R.id.container, detailsFragment)
                .commit()
    }

    private fun setupTitle() {
        setTitle(getString(R.string.details))
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        super.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun provideSubContentViewId() = R.layout.activity_details

    override fun onBackPressed() = with(detailsFragment) {
        when(canGoBack()) {
            true -> goBack()
            false -> super.onBackPressed()
        }
    }
}
