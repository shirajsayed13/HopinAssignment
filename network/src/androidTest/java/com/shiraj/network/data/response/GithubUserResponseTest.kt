package com.shiraj.network.data.response

import com.shiraj.core.model.GithubUserModel
import com.shiraj.network.response.GithubUserResponse
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
internal class GithubUserResponseTest {

    @Test
    fun userResponseWithFullDataMapsToUserDomainModel() {

        val userModel = GithubUserModel.Item(
            avatarUrl = "https://avatars.githubusercontent.com/u/25314883?v=4",
            id = 25314883,
            login = "shirajsayed",
            type = "user"
        )

        val userResponse = GithubUserResponse.Item(
            avatarUrl = "https://avatars.githubusercontent.com/u/25314883?v=4",
            id = 25314883,
            login = "shirajsayed",
            type = "user"
        )
        assertEquals(userResponse, userModel)
    }
}