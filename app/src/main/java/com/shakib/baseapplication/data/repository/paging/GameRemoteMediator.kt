package com.shakib.baseapplication.data.repository.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.shakib.baseapplication.common.extensions.printInfoLog
import com.shakib.baseapplication.common.utils.HelperConstants.STARTING_INDEX
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.model.PageKey
import com.shakib.baseapplication.data.network.GameApi
import com.shakib.baseapplication.data.room.game.GameDao
import com.shakib.baseapplication.data.room.game.PageDao
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GameRemoteMediator(
    private val gameApi: GameApi,
    private val gameDao: GameDao,
    private val pageDao: PageDao
) : RemoteMediator<Int, Game>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Game>): MediatorResult {
        printInfoLog("Called with LoadType - ${loadType.name}")
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val pageKey = getPageKeyClosestToCurrentPosition(state)
                pageKey?.nextKey?.minus(1) ?: STARTING_INDEX
            }
            LoadType.PREPEND -> {
                val pageKey = getPageKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                val prevKey = pageKey?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = pageKey != null)
                prevKey
            }
            LoadType.APPEND -> {
                val pageKey = getPageKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with endOfPaginationReached = false because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its nextKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = pageKey?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = pageKey != null)
                nextKey
            }
        }
        return try {
            val response = gameApi.getGamesPageByPage(
                "2020-01-01,2020-12-31",
                "-added",
                page,
                state.config.pageSize
            )
            val gameList = response.results
            if (loadType == LoadType.REFRESH) {
                pageDao.clearPageKeys()
                gameDao.clearGames()
            }
            val prevKey = if (page == STARTING_INDEX) null else page - 1
            val nextKey = if (response.next == "") null else page + 1
            val keys = gameList.map {
                PageKey(key = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            pageDao.insertAllPageKeys(keys)
            gameDao.saveGameList(gameList)
            MediatorResult.Success(endOfPaginationReached = response.next == "")
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

    private suspend fun getPageKeyClosestToCurrentPosition(
        state: PagingState<Int, Game>
    ): PageKey? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { gameId ->
                pageDao.fetchPageKey(gameId)
            }
        }
    }

    private suspend fun getPageKeyForFirstItem(state: PagingState<Int, Game>): PageKey? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { game ->
                // Get the remote keys of the first items retrieved
                pageDao.fetchPageKey(game.id)
            }
    }

    private suspend fun getPageKeyForLastItem(state: PagingState<Int, Game>): PageKey? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { game ->
                // Get the remote keys of the last item retrieved
                pageDao.fetchPageKey(game.id)
            }
    }

}
