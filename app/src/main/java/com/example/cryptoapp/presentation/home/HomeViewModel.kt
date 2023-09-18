package com.example.cryptoapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ItemSnapshotList
import androidx.paging.cachedIn
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.domain.usecase.GetCoinListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getCoinListUseCase: GetCoinListUseCase
) : ViewModel() {

    val coinState = getCoinListUseCase.execute()
        .cachedIn(viewModelScope)

    val filterState = MutableStateFlow(SortFilter.EMPTY)

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    fun setSearch(query: String) {
        _searchQuery.value = query
    }

    fun getSearchedResult(
        list: ItemSnapshotList<CoinUiModel>,
        filter: SortFilter
    ): List<CoinUiModel> {
        val result = mutableListOf<CoinUiModel>()

        if (searchQuery.value.isNotEmpty()) {
            val searchedList = list.items.filter {
                it.fullName.lowercase().contains(searchQuery.value.lowercase())
            }
            result.addAll(searchedList)
        }

        when (filter) {
            SortFilter.DESCENDING_PRICE -> {
                result.sortByDescending { it.price }
            }

            SortFilter.ASCENDING_PRICE -> {
                result.sortBy { it.price }
            }

            SortFilter.DESCENDING_NAME -> {
                result.sortByDescending { it.fullName }
            }

            SortFilter.ASCENDING_NAME -> {
                result.sortBy { it.fullName }
            }

            else -> {
                emptyList<CoinUiModel>()
            }
        }

        return result
    }

    fun filterData(
        list: ItemSnapshotList<CoinUiModel>,
        filterType: SortFilter
    ): MutableList<CoinUiModel?> {
        val result = list.toMutableList()

        when (filterType) {
            SortFilter.DESCENDING_PRICE -> {
                result.sortByDescending { it!!.price }
            }

            SortFilter.ASCENDING_PRICE -> {
                result.sortBy { it!!.price }
            }

            SortFilter.DESCENDING_NAME -> {
                result.sortByDescending { it!!.fullName }
            }

            SortFilter.ASCENDING_NAME -> {
                result.sortBy { it!!.fullName }
            }

            else -> {
                emptyList<CoinUiModel>()
            }
        }
        return result
    }
}


enum class SortFilter {
    EMPTY,
    ASCENDING_PRICE,
    DESCENDING_PRICE,
    ASCENDING_NAME,
    DESCENDING_NAME,
}
