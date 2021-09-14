package com.shiraj.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GithubUserModel(
    val incompleteResults: Boolean,
    val items: List<Item>,
    val totalCount: Int
) : Parcelable {

    @Parcelize
    data class Item(
        val avatarUrl: String,
        val id: Int,
        val login: String,
        val type: String,
        val url: String
    ) : Parcelable
}