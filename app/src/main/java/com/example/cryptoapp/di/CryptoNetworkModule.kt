package com.example.cryptoapp.di

import com.example.cryptoapp.BuildConfig
import com.example.cryptoapp.data.api.CryptoApi
import com.example.cryptoapp.core.util.Const
import com.example.cryptoapp.core.util.Const.CRYPTO_API_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CryptoNetworkModule {

    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        encodeDefaults = true
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun getInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    @Provides
    @Singleton
    fun getHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideWebSocketRequest(): Request {
        return Request.Builder()
            .url(Const.CRYPTO_API_WEBSOCKET_URL + BuildConfig.API_KEY)
            .build()
    }

    @Provides
    @Singleton
    fun provideCryptoApi(client: OkHttpClient): CryptoApi {

        return Retrofit.Builder()
            .baseUrl(CRYPTO_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(CryptoApi::class.java)
    }

}