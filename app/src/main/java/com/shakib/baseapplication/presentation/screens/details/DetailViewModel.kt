package com.shakib.baseapplication.presentation.screens.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shakib.baseapplication.common.base.BaseViewModel
import com.shakib.baseapplication.common.extensions.printErrorLog
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.model.ScreenShot
import com.shakib.baseapplication.domain.FetchGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchGameDetailsUseCase: FetchGameDetailsUseCase
): BaseViewModel() {

    val gameDetailsLiveData by lazy { MutableLiveData<Resource<Game>>() }
    val gameScreenShotsLiveData by lazy { MutableLiveData<List<ScreenShot>>() }

    fun fetchGameDetails(gameId: Int) {
        viewModelScope.launch {
            fetchGameDetailsUseCase.apply {
                gameDetailsLiveData.value = Resource.Loading()
                fetchGameDetails(gameId)
                    .catch { gameDetailsLiveData.value = Resource.Error(it) }
                    .collect { gameDetailsLiveData.value = Resource.Success(it) }

                fetchGameScreenShots(gameId)
                    .catch { printErrorLog(it.message.toString()) }
                    .collect { gameScreenShotsLiveData.value = it.results }
            }
        }
    }

    override fun onClear() {}
}