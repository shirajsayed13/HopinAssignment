package com.shiraj.gui.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shiraj.core.model.GithubUserModel
import com.shiraj.gui.databinding.TileUserBinding
import com.shiraj.gui.loadUrl
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject
import kotlin.properties.Delegates

@FragmentScoped
class SearchResultAdapter @Inject constructor() :
    RecyclerView.Adapter<SearchResultAdapter.SearchResultVH>() {

    var items: List<GithubUserModel.Item> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    class SearchResultVH(private val binding: TileUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(info: GithubUserModel.Item) {
            binding.apply {
                tvUserName.text = info.login
                ivUserPhoto.loadUrl(info.avatarUrl)
                tvType.text = info.type
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchResultVH(
        TileUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SearchResultVH, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size
}