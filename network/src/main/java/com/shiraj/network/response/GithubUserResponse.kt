package com.shiraj.network.response


import com.shiraj.core.model.GithubUserModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

internal fun GithubUserResponse.toGithubUserResponse() = GithubUserModel(
    incompleteResults = incompleteResults,
    items = items.map { it.toItemList() },
    totalCount = totalCount
)

internal fun GithubUserResponse.Item.toItemList() = GithubUserModel.Item(
    id = id,
    avatarUrl = avatarUrl,
    login = login,
    type = type,
)

@JsonClass(generateAdapter = true)
data class GithubUserResponse(
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean,
    @Json(name = "items")
    val items: List<Item>,
    @Json(name = "total_count")
    val totalCount: Int
) {
    @JsonClass(generateAdapter = true)
    data class Item(
        @Json(name = "avatar_url")
        val avatarUrl: String,
        @Json(name = "id")
        val id: Int,
        @Json(name = "login")
        val login: String,
        @Json(name = "type")
        val type: String
    )
}