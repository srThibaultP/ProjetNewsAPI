package io.github.srthibaultp.projetnewsapi.data.internal

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.github.srthibaultp.projetnewsapi.data.network.modele.Article
import io.github.srthibaultp.projetnewsapi.StatusAPI

class ArticlePagingSource( private val func: suspend (position: Int, loadSize: Int) -> StatusAPI) : PagingSource<Int, Article>()
{
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article>
    {
        val position = params.key ?: STARTING_PAGE_INDEX
        return when (val apiEvent = func.invoke(position, params.loadSize))
        {
            is StatusAPI.Erreur -> LoadResult.Error(Throwable(apiEvent.message))
            is StatusAPI.Succes -> LoadResult.Page(
                data = apiEvent.data,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (apiEvent.data.isEmpty()) null else position + 1
            )
            else -> LoadResult.Error(Throwable("Pas de données"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? { return null }
}

private const val STARTING_PAGE_INDEX = 1