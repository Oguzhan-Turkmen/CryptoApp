package com.example.cryptoapp.presentation.news

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.cryptoapp.domain.model.CoinNewUiModel
import com.example.cryptoapp.domain.usecase.GetCoinNewsUseCase
import com.example.cryptoapp.core.util.Const.PARAM_COIN_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getCoinNewsUseCase: GetCoinNewsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _coinNewsData = MutableStateFlow<List<CoinNewUiModel>>(emptyList())
    val coinNewsData = _coinNewsData.asStateFlow()

    init {
        savedStateHandle.get<String>(PARAM_COIN_NAME)?.let {
            getCoinNews(it)
        }
    }

    private fun getCoinNews(coinName: String) {
        viewModelScope.launch {
            _coinNewsData.value =
                getCoinNewsUseCase.execute(
                    coinName = coinName,
                )
        }
    }
}