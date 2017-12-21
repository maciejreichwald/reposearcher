package com.rudearts.reposearcher.ui.repo.list

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.rudearts.reposearcher.R
import com.rudearts.reposearcher.databinding.RepoItemBinding
import com.rudearts.reposearcher.extentions.loadUrlThumb
import com.rudearts.reposearcher.model.local.Repo
import kotlinx.android.synthetic.main.repo_item.view.*

class RepoListAdapter(context:Context) : ArrayAdapter<Repo>(context, R.layout.repo_item) {

    companion object {
        val DEFAULT_IMAGE_SIZE = 150
        val DEFAULT_AVATAR_RESOURCE = R.drawable.user
    }

    private var repos:ArrayList<Repo> = ArrayList()
    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val repo = repos[position]
        val binding = createViewBinding()
        binding.repo = repo
        binding.root.forks.text = repo.forks.toString()
        binding.root.favorite_check.isSelected = repo.favorite
        binding.root.favorite_container.setOnClickListener { onCheckClick(binding.root.favorite_check, position) }

        createDescritpion(repo, binding.root.description)
        loadAvatar(repo.owner.avatar, binding.root.avatar)

        return binding.root
    }

    private fun onCheckClick(check: View?, position: Int) {
        val selected = check?.isSelected ?: false
        check?.isSelected = !selected

        val updatedRepo = repos[position].copy(favorite = !selected)
        repos[position] = updatedRepo
    }

    override fun getItem(position:Int) = repos[position]

    override fun getCount() = repos.size

    fun getFavoriteItems() = repos.filter { it.favorite }

    fun updateItems(items:ArrayList<Repo>) {
        repos = items
        notifyDataSetChanged()
    }

    private fun createDescritpion(repo: Repo, description: TextView?) {
        val text:String = repo.description ?: repo.owner.login
        description?.text = text
    }

    private fun loadAvatar(avatarUrl: String?, avatarView: ImageView) {
        avatarView.setImageResource(DEFAULT_AVATAR_RESOURCE)
        avatarUrl?.let {
            avatarView.loadUrlThumb(DEFAULT_IMAGE_SIZE, DEFAULT_AVATAR_RESOURCE, it)
        }
    }

    internal fun createViewBinding(): RepoItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.repo_item, null, false)
}