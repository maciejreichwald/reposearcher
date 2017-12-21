package com.rudearts.reposearcher.ui.details.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mindorks.nybus.NYBus
import com.mindorks.nybus.annotation.Subscribe
import com.mindorks.nybus.thread.NYThread
import com.rudearts.reposearcher.R
import com.rudearts.reposearcher.extentions.bind
import com.rudearts.reposearcher.extentions.visible
import com.rudearts.reposearcher.model.DetailsEvent
import com.rudearts.reposearcher.model.LoadingState

class DetailsFragment : Fragment() {

    companion object {
        val LINK = "LinkParam"
        val START_STATE = "DefaultState"
    }

    private val emptyView:View? by bind(R.id.empty_view)
    private val progressBar:View? by bind(R.id.progress_bar)
    private val webView: WebView? by bind(R.id.web_view)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        restoreState(savedInstanceState)
    }

    private fun restoreState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            webView?.restoreState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveState(outState)
    }

    private fun saveState(outState: Bundle) {
        webView?.saveState(outState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        val isLoading = arguments?.getInt(START_STATE) == LoadingState.LOADING.ordinal

        when(isLoading) {
            true -> changeWebViewVisibility(LoadingState.LOADING)
            false -> setupContent()
        }

        NYBus.get().register(this)
    }

    private fun setupContent() {
        val link = arguments?.getString(LINK)

        setupWhenValidLink(link)
    }

    private fun setupWhenValidLink(link: String?) {
        when(TextUtils.isEmpty(link)) {
            true -> changeWebViewVisibility(LoadingState.NO_RESULTS)
            false -> setupWebView(link!!)
        }
    }

    private fun changeWebViewVisibility(state: LoadingState) {
        progressBar?.visible = state == LoadingState.LOADING
        webView?.visible = state == LoadingState.SHOW_RESULTS
        emptyView?.visible = state == LoadingState.NO_RESULTS
    }

    private fun setupWebView(link:String) {
        changeWebViewVisibility(LoadingState.LOADING)
        webView?.let {
            it.webViewClient = createWebViewClient()
            it.loadUrl(link)
        }
    }

    private fun createWebViewClient() = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            changeWebViewVisibility(LoadingState.SHOW_RESULTS)
            super.onPageFinished(view, url)
        }

        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            changeWebViewVisibility(LoadingState.NO_RESULTS)
            super.onReceivedError(view, request, error)
        }
    }

    fun canGoBack() = webView?.canGoBack() ?: false

    fun goBack() {
        webView?.goBack()
    }

    @Subscribe(threadType = NYThread.MAIN)
    fun onEvent(event:DetailsEvent) {
        setupWhenValidLink(event.link)
    }
}
