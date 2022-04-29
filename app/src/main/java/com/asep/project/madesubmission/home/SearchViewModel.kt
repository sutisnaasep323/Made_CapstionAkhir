package com.asep.project.madesubmission.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.asep.project.core.domain.usecase.MovieAppUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

@ObsoleteCoroutinesApi
@FlowPreview
@ExperimentalCoroutinesApi
class SearchViewModel(private val movieAppUseCase: MovieAppUseCase) : ViewModel() {

    private val queryChannel = ConflatedBroadcastChannel<String>()

    fun setSearchQuery(search: String) {
        queryChannel.trySend(search).isSuccess
    }

    val movieResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieAppUseCase.getSearchMovies(it)
        }.asLiveData()

    val tvShowResult = queryChannel.asFlow()
        .debounce(300)
        .distinctUntilChanged()
        .filter {
            it.trim().isNotEmpty()
        }
        .flatMapLatest {
            movieAppUseCase.getSearchTvShows(it)
        }.asLiveData()
}