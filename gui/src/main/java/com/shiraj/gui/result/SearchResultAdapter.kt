package com.shiraj.gui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.core.model.GithubUserModel
import com.shiraj.gui.databinding.TileUserBinding
import com.shiraj.gui.loadUrl
import kotlin.properties.Delegates

class SearchResultAdapter :
    PagingDataAdapter<GithubUserModel.Item, SearchResultAdapter.SearchResultVH>(SearchResultViewDiff()) {

    class SearchResultVH(private val binding: TileUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(info: GithubUserModel.Item) {
            binding.apply {
                ivUserPhoto.loadUrl(info.avatarUrl)
                tvUserName.text = info.login
                tvType.text = info.type
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchResultVH(
        TileUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SearchResultVH, position: Int) {
        val searchResults = getItem(position)
        if (searchResults != null) {
            holder.bind(searchResults)
        }
    }

    class SearchResultViewDiff : DiffUtil.ItemCallback<GithubUserModel.Item>() {
        override fun areItemsTheSame(oldItem: GithubUserModel.Item, newItem: GithubUserModel.Item): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: GithubUserModel.Item, newItem: GithubUserModel.Item): Boolean {
            return oldItem == newItem
        }
    }
}