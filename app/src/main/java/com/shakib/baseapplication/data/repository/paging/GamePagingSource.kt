package com.shakib.baseapplication.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shakib.baseapplication.common.extensions.printErrorLog
import com.shakib.baseapplication.common.extensions.printInfoLog
import com.shakib.baseapplication.common.utils.HelperConstants.NETWORK_PAGE_SIZE
import com.shakib.baseapplication.common.utils.HelperConstants.STARTING_INDEX
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.network.GameApi
import retrofit2.HttpException
import java.io.IOException

class GamePagingSource(private val gameApi: GameApi) : PagingSource<Int, Game>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Game> {
        val key = params.key ?: STARTING_INDEX
        return try {
            val response = gameApi.getGamesPageByPage(key, params.loadSize)
            val gameList = response.results
            printInfoLog("Call no $key | List size ${gameList.size}")
            val nextKey = if (gameList.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                key + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = gameList,
                prevKey = if (key == STARTING_INDEX) null else key - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            printErrorLog(exception.message.toString())
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            printErrorLog(exception.message.toString())
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Game>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}