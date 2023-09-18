package com.example.cryptoapp.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cryptoapp.data.api.CryptoApi
import com.example.cryptoapp.data.dto.coin.CoinResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoinsPagingSource(
    private val cryptoApi: CryptoApi,
    private val tsym: String,
) : PagingSource<Int, CoinResponse>() {
    override fun getRefreshKey(state: PagingState<Int, CoinResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinResponse> {
        return try {
            val page = params.key ?: 0
            val response = withContext(Dispatchers.IO) {
                cryptoApi.getAllCoin(page = page, tsym = tsym)
            }

            LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = if (response.data.isEmpty()) null else page.plus(1),
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}