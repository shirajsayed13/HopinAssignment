package com.shiraj.gui.result

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.shiraj.core.model.GithubUserModel
import com.shiraj.core.usecase.GetGithubUserUseCase
import java.io.IOException

class SearchResultPageSource(
    private val getSearchUseCase: GetGithubUserUseCase,
    private val keyword: String,
) : PagingSource<Int, GithubUserModel.Item>() {

    private val INDEX_PAGE = 1

    override fun getRefreshKey(state: PagingState<Int, GithubUserModel.Item>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUserModel.Item> {
        val page = params.key ?: INDEX_PAGE
        return try {
            val response = getSearchUseCase.invoke(keyword, page.toString())
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                page + 1
            }
            LoadResult.Page(
                response,
                prevKey = if (page == INDEX_PAGE) null else page - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}