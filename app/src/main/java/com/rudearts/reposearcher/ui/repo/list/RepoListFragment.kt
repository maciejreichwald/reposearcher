package com.rudearts.reposearcher.ui.repo.list


import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.mindorks.nybus.NYBus
import com.rudearts.reposearcher.R
import com.rudearts.reposearcher.RepoApplication
import com.rudearts.reposearcher.di.main.DaggerRepoListComponent
import com.rudearts.reposearcher.di.main.RepoListModule
import com.rudearts.reposearcher.extentions.bind
import com.rudearts.reposearcher.extentions.visible
import com.rudearts.reposearcher.model.LoadingState
import com.rudearts.reposearcher.model.local.Repo
import com.rudearts.reposearcher.ui.details.DetailsActivity
import javax.inject.Inject


class RepoListFragment: Fragment(), RepoListContract.View {

    companion object {
        val USERNAME = "QueryParam"
        val DETAILS_URL = "DetailsUrl"
    }

    private val refreshLayout: SwipeRefreshLayout? by bind(R.id.swipe_container)
    private val progressBar: View? by bind(R.id.progress_bar)
    private val listItems: ListView? by bind(R.id.items_list)
    private val emptyView: View? by bind(R.id.empty_view)

    @Inject lateinit var presenter:RepoListContract.Presenter

    private var adapter:RepoListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_repo_list, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup(savedInstanceState)
    }

    internal fun setup(savedInstanceState: Bundle?) {
        inject()
        restoreData(savedInstanceState)
        setupRefresh()
        setupList()
        loadItems()
    }

    private fun inject() {
        val componetnt = DaggerRepoListComponent.builder()
                .appComponent(RepoApplication.appComponent)
                .repoListModule(RepoListModule(this))
                .build()
        componetnt.inject(this)
    }

    private fun setupList() {
        context?.let {
            adapter = RepoListAdapter(it)
            listItems?.adapter = adapter
            listItems?.setOnItemClickListener { _, _, position, _ -> onItemClick(position) }
        }
    }

    private fun onItemClick(position: Int) {
        adapter?.let {
            presenter.onRepoClick(it.getItem(position))
        }
    }

    internal fun setupRefresh() {
        refreshLayout?.setOnRefreshListener {
            loadItems()
            refreshLayout?.isRefreshing = false
        }
    }

    internal fun loadItems() {
        val query = arguments?.get(USERNAME) as String? ?: ""
        presenter.loadRepos(query)
    }

    override fun changeListVisibility(state: LoadingState) {
        progressBar?.visible = state == LoadingState.LOADING
        emptyView?.visible = state == LoadingState.NO_RESULTS
        listItems?.visible = state == LoadingState.SHOW_RESULTS
    }

    override fun updateRepos(repos: ArrayList<Repo>) {
        adapter?.updateItems(repos)
    }

    override fun isLandscape() = isAdded and (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)

    override fun showDetailsActivity(args: Bundle) {
        Intent(activity, DetailsActivity::class.java).apply {
            putExtras(args)
            startActivity(this)
        }
    }

    override fun onDestroyView() {
        presenter.saveFavorites(adapter?.getFavoriteItems())

        super.onDestroyView()
    }

    private fun restoreData(savedInstanceState: Bundle?) {
        val url = savedInstanceState?.getString(DETAILS_URL)
        presenter.restoreStoredUrl(url)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(DETAILS_URL, presenter.retrieveStoredUrl())
    }
}
