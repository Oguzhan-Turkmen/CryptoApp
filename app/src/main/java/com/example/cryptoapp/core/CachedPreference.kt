package com.example.cryptoapp.core

import android.content.SharedPreferences
import androidx.core.content.edit

class CachedPreference<T>(
    private val sharedPreferences: SharedPreferences,
    private val preferenceKey: PreferenceKey,
    private val defaultValue: T
) {
    private var cachedValue: T? = null

    fun read(): T =
        cachedValue ?: getDataFromSharedPreferences(preferenceKey.key)

    fun write(data: T) {
        cachedValue = data
        writeDataToSharedPreferences(data, preferenceKey.key)
    }

    fun getAll(): List<String> {
        val list = ArrayList<String>()
        sharedPreferences.all.forEach {
            list.add(it.value.toString())
        }
        return list
    }

    fun clearSpecData(coinName: String) {
        sharedPreferences.edit().remove(coinName).commit()
    }

    fun clear() {
        cachedValue = null
        sharedPreferences.edit().remove(preferenceKey.key).apply()
    }

    fun clearAll() {
        cachedValue = null
        sharedPreferences.edit {
            clear()
        }
    }

    private fun writeDataToSharedPreferences(data: T, key: String) {
        sharedPreferences.edit {
            when (data) {
                is Int -> putInt(key, data)
                is String -> putString(key, data)
                is Float -> putFloat(key, data)
                is Boolean -> putBoolean(key, data)
                is Long -> putLong(key, data)
                else -> throw IllegalArgumentException(
                    "Illegal value type $defaultValue for key \"$key\""
                )
            }
        }
    }

    private fun getDataFromSharedPreferences(key: String): T = sharedPreferences.let {
        when (defaultValue) {
            is Int -> it.getInt(key, defaultValue as Int) as T
            is String -> it.getString(key, defaultValue as String) as T
            is Float -> it.getFloat(key, defaultValue as Float) as T
            is Boolean -> it.getBoolean(key, defaultValue as Boolean) as T
            is Long -> it.getLong(key, defaultValue as Long) as T
            else -> throw IllegalArgumentException(
                "Illegal value type $defaultValue for key \"$key\""
            )
        }
    }
}

enum class PreferenceKey(val key: String) {
    SAVED_COINS("saved_coins")
}