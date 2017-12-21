package com.rudearts.reposearcher.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import com.rudearts.reposearcher.R
import com.rudearts.reposearcher.extentions.bind
import com.rudearts.reposearcher.ui.repo.RepoActivity
import com.rudearts.reposearcher.ui.repo.list.RepoListFragment
import com.rudearts.reposearcher.util.BaseTextWatcher


class MainActivity : AppCompatActivity() {

    companion object {
        val SAVED_ERROR = "SavedError"
    }

    internal val inputSearch:EditText by bind(R.id.search_input)
    internal val btnSearch:View by bind(R.id.search_btn)

    internal lateinit var inputError:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup(savedInstanceState)
    }

    internal fun setup(savedInstanceState: Bundle?) {
        inputError = getString(R.string.username_invalid)

        btnSearch.setOnClickListener { onSearchClick() }

        inputSearch.addTextChangedListener(createTextWatcher(inputSearch))
        inputSearch.setOnEditorActionListener { textView, i, keyEvent ->
            onSearchClick()
            true
        }
        inputSearch.error = restoreError(savedInstanceState)
    }

    private fun restoreError(savedInstanceState: Bundle?):String? = savedInstanceState?.let {
        it.getString(SAVED_ERROR)
    }

    internal fun onSearchClick() {
        val text = inputSearch.editableText.toString()

        when (isInvalid(text)) {
            true -> inputSearch.error = inputError
            false -> moveToRepoView(text)
        }
    }

    internal fun moveToRepoView(text: String) {
        Intent(this, RepoActivity::class.java).apply {
            putExtra(RepoListFragment.USERNAME, text)
            startActivity(this)
        }
    }

    private fun isInvalid(text: String) = text.isEmpty() or text.contains(" ")

    private fun createTextWatcher(input: EditText) = object : BaseTextWatcher() {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            input.error = null
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putString(SAVED_ERROR, inputSearch.error as String?)
    }
}
