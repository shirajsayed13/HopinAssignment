package com.shiraj.core.webservice

import com.shiraj.core.model.GithubUserModel


interface GithubUserWS {
    suspend fun getGithubUserWS(searchKeyword: String): List<GithubUserModel.Item>
}