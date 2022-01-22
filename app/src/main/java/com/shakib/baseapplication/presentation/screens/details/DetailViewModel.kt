package com.shakib.baseapplication.presentation.screens.details

import androidx.lifecycle.viewModelScope
import com.shakib.baseapplication.common.base.BaseViewModel
import com.shakib.baseapplication.common.utils.Resource
import com.shakib.baseapplication.data.model.Game
import com.shakib.baseapplication.data.model.ScreenShot
import com.shakib.baseapplication.domain.FetchGameDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchGameDetailsUseCase: FetchGameDetailsUseCase
) : BaseViewModel() {

    val gameDetailsStateFlow = MutableStateFlow<Resource<Game>>(Resource.Loading())
    val gameScreenShotsStateFlow = MutableStateFlow<Resource<List<ScreenShot>>>(Resource.Loading())

    fun fetchGameDetails(gameId: Int) {
        viewModelScope.launch {
            fetchGameDetailsUseCase.apply {
                fetchGameDetails(gameId)
                    .catch { gameDetailsStateFlow.value = Resource.Error(it) }
                    .collect { gameDetailsStateFlow.value = Resource.Success(it) }

                fetchGameScreenShots(gameId)
                    .catch { gameScreenShotsStateFlow.value = Resource.Error(it) }
                    .collect { gameScreenShotsStateFlow.value = Resource.Success(it.results) }
            }
        }
    }

    override fun onClear() {}
}