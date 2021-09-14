package com.shiraj.core.usecase

import com.shiraj.core.model.GithubUserModel
import com.shiraj.core.webservice.GithubUserWS
import javax.inject.Inject

class GetWeatherInfoUseCase @Inject constructor(
    private val githubUserWS: GithubUserWS
) {

    suspend operator fun invoke(): List<GithubUserModel.Item> {
        return githubUserWS.getGithubUserWS()
    }
}