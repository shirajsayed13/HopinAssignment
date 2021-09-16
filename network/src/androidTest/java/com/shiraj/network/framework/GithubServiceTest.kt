package com.shiraj.network.framework

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.shiraj.network.service.listing.RetrofitGithubUserWebService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import okio.buffer
import okio.source
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

@RunWith(JUnit4::class)
class GithubServiceTest {

    private lateinit var service: RetrofitGithubUserWebService
    private lateinit var mockWebServer: MockWebServer

    @Throws(IOException::class)
    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(RetrofitGithubUserWebService::class.java)

        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) = runnable.run()
            override fun isMainThread() = true
            override fun postToMainThread(runnable: Runnable) = runnable.run()
        })
    }

    @Throws(IOException::class)
    @After
    fun stopService() {
        mockWebServer.shutdown()
        ArchTaskExecutor.getInstance().setDelegate(null)
    }


    @Test
    fun getGithubUserTest() {
        enqueueResponse("user-shirajsayed.json")
        GlobalScope.launch {
            val githubUser = service.getGithubUsers("shirajsayed", "1", "1").items[0]
            val request: RecordedRequest = mockWebServer.takeRequest()

            MatcherAssert.assertThat(request.path, `is`("/users/shirajsayed"))
            MatcherAssert.assertThat(githubUser, notNullValue())
            MatcherAssert.assertThat(githubUser.login, `is`("shirajsayed"))
            MatcherAssert.assertThat(githubUser.id, `is`(25314883))
            MatcherAssert.assertThat(
                githubUser.avatarUrl,
                `is`("https://avatars.githubusercontent.com/u/25314883?v=4")
            )
        }
    }


    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String) {
        enqueueResponse(fileName, emptyMap())
    }

    @Throws(IOException::class)
    private fun enqueueResponse(fileName: String, headers: Map<String, String>) {
        val inputStream = javaClass.classLoader.getResourceAsStream("api-response/$fileName")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(source.readString(StandardCharsets.UTF_8)))
    }

}