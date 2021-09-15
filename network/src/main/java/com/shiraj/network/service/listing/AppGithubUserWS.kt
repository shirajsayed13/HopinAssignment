package com.shiraj.network.service.listing

import com.shiraj.core.model.GithubUserModel
import com.shiraj.core.webservice.GithubUserWS
import com.shiraj.network.Constants.Companion.PER_PAGE
import com.shiraj.network.response.toItemList
import com.shiraj.network.service.networkCall
import javax.inject.Inject

internal class AppGithubUserWS @Inject constructor(
    private val githubUserWebService: RetrofitGithubUserWebService
) : GithubUserWS {

    override suspend fun getGithubUserWS(
        searchKeyword: String,
        page: String
    ): List<GithubUserModel.Item> =
        networkCall(
            { githubUserWebService.getGithubUsers(searchKeyword, PER_PAGE, page) },
            { response -> response.items.map { it.toItemList() } }
        )
}