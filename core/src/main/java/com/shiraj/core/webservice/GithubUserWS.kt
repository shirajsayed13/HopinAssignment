package com.shiraj.core.webservice

import com.shiraj.core.model.GithubUserModel


interface GithubUserWS {
    suspend fun getGithubUserWS(): List<GithubUserModel.Item>
}