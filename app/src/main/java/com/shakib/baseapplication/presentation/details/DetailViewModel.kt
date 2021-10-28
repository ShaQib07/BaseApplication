package com.shakib.baseapplication.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.shakib.baseapplication.common.base.BaseViewModel
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
    val gameScreenShotsLiveData by lazy { MutableLiveData<Resource<List<ScreenShot>>>() }

    fun fetchGameDetails(gameId: Int) {
        viewModelScope.launch {
            gameScreenShotsLiveData.value = Resource.Loading()
            fetchGameDetailsUseCase.fetchGameScreenShots(gameId)
                .catch { gameScreenShotsLiveData.value = Resource.Error(it) }
                .collect { gameScreenShotsLiveData.value = Resource.Success(it.results) }

            gameDetailsLiveData.value = Resource.Loading()
            fetchGameDetailsUseCase.fetchGameDetails(gameId)
                .catch { gameDetailsLiveData.value = Resource.Error(it) }
                .collect { gameDetailsLiveData.value = Resource.Success(it) }

        }
    }

    override fun onClear() {}
}