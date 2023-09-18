package com.example.cryptoapp.presentation.home.components

import com.example.cryptoapp.presentation.home.SortFilter

data class FilterModel(
    val filterId: SortFilter,
    val filterName: String
)

val filterModel = listOf(
    FilterModel(filterId = SortFilter.EMPTY, filterName = "Default"),
    FilterModel(filterId = SortFilter.ASCENDING_PRICE, filterName = "Price(Low-High)"),
    FilterModel(filterId = SortFilter.DESCENDING_PRICE, filterName = "Price(High-Low)"),
    FilterModel(filterId = SortFilter.ASCENDING_NAME, filterName = "Name(A-Z)"),
    FilterModel(filterId = SortFilter.DESCENDING_NAME, filterName = "Name(Z-A)"),
)