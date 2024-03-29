package com.example.cryptoapp.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "CoinEntity")
data class CoinUiModel(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val fullName: String,
    val imageUrl: String,
    val priceWithSymbol: String,
    val price: Double,
    val changePctDay: Double,
    val changeDay: Double,
    val toSymbol: String
) : Parcelable