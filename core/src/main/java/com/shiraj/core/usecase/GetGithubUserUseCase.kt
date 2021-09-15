package com.shiraj.core.usecase

import com.shiraj.core.model.GithubUserModel
import com.shiraj.core.webservice.GithubUserWS
import javax.inject.Inject

class GetGithubUserUseCase @Inject constructor(
    private val githubUserWS: GithubUserWS
) {

    suspend operator fun invoke(searchKeyword: String): List<GithubUserModel.Item> {
        return githubUserWS.getGithubUserWS(searchKeyword)
    }
}