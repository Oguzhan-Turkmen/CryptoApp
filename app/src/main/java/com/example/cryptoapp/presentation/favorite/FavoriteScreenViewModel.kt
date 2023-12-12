package com.example.cryptoapp.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.domain.usecase.GetSavedCoinsDbUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val getSavedCoinsDbUseCase: GetSavedCoinsDbUseCase,
) : ViewModel() {

    private val _savedCoinList = MutableStateFlow<List<CoinUiModel>>(emptyList())
    val savedCoinList = _savedCoinList.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    fun getSavedCoins() {
        CoroutineScope(Dispatchers.IO).launch {
            _savedCoinList.value = getSavedCoinsDbUseCase.execute()
        }
    }

    fun setSearch(query: String) {
        _searchQuery.value = query
    }

    fun getSearchedResult(
        list: List<CoinUiModel>,
    ): List<CoinUiModel> {
        val result = mutableListOf<CoinUiModel>()

        if (searchQuery.value.isNotEmpty()) {
            val searchedList = list.filter {
                it.fullName.lowercase().contains(searchQuery.value.lowercase())
            }
            result.addAll(searchedList)
        }
        return result
    }
}