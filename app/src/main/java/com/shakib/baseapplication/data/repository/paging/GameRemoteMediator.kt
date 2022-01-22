package com.shakib.baseapplication.data.repository.paging


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.shakib.baseapplication.common.extensions.printDebugLog
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
        return try {
            // The network load method takes an optional `after=<user.id>` parameter. For every
            // page after the first, we pass the last user ID to let it continue from where it
            // left off. For REFRESH, pass `null` to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                // In this example, we never need to prepend, since REFRESH will always load the
                // first page in the list. Immediately return, reporting end of pagination.
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = pageDao.fetchPageKey().lastOrNull()
                    // We must explicitly check if the page key is `null` when appending,
                    // since `null` is only valid for initial load. If we receive `null`
                    // for APPEND, that means we have reached the end of pagination and
                    // there are no more items to load.
                    if (remoteKey?.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextKey
                }
            }

            val page = loadKey ?: STARTING_INDEX
            printDebugLog("loadType - $loadType |loadKey - $page")
            // Suspending network load via Retrofit. This doesn't need to be wrapped in a
            // withContext(Dispatcher.IO) { ... } block since Retrofit's Coroutine CallAdapter
            // dispatches on a worker thread.
            val response = gameApi.getGamesPageByPage(page, state.config.pageSize)

            if (loadType == LoadType.REFRESH) {
                gameDao.clearGames()
                pageDao.clearPageKeys()
            }

            // Insert new users into database, which invalidates the current
            // PagingData, allowing Paging to present the updates in the DB.
            gameDao.saveGameList(response.results)
            pageDao.insertPageKey(PageKey(page, page - 1, page + 1))


            MediatorResult.Success(endOfPaginationReached = response.next.isEmpty())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
