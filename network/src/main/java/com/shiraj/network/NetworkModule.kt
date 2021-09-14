package com.shiraj.network

import android.content.Context
import android.net.ConnectivityManager
import androidx.viewbinding.BuildConfig
import com.shiraj.core.webservice.GithubUserWS
import com.shiraj.network.GithubBaseUrl.baseUrlFromJNI
import com.shiraj.network.framework.AndroidNetworkConnectionMonitor
import com.shiraj.network.framework.NetworkConnectionMonitor
import com.shiraj.network.framework.NetworkConnectionMonitorInterceptor
import com.shiraj.network.service.listing.AppGithubUserWS
import com.shiraj.network.service.listing.RetrofitGithubUserWebService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    internal fun provideBaseUrl(): String {

        object {
            init {
                System.loadLibrary("network")
            }
        }
        return baseUrlFromJNI()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(
        builder: Retrofit.Builder,
        okHttpClient: OkHttpClient,

        ): Retrofit {
        return builder
            .baseUrl(provideBaseUrl())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()

    @Provides
    @Singleton
    internal fun provideOkHttpClient(builder: OkHttpClient.Builder): OkHttpClient =
        builder.build()

    @Provides
    @Singleton
    internal fun provideOkHttpClientBuilder(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        networkConnectionMonitorInterceptor: NetworkConnectionMonitorInterceptor,
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(httpLoggingInterceptor)
            }
            addNetworkInterceptor(networkConnectionMonitorInterceptor)
        }
    }

    @Provides
    @Singleton
    internal fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)


    @Provides
    @Singleton
    internal fun provideNetworkConnectionMonitor(@ApplicationContext context: Context): NetworkConnectionMonitor =
        AndroidNetworkConnectionMonitor(context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)


    @Provides
    @Singleton
    internal fun provideRetrofitGithubUserWebService(retrofit: Retrofit): RetrofitGithubUserWebService =
        retrofit.create(RetrofitGithubUserWebService::class.java)


    @Module
    @InstallIn(SingletonComponent::class)
    internal interface AppWebService {
        @Binds
        @Singleton
        abstract fun bindGithubUserWebService(service: AppGithubUserWS): GithubUserWS
    }

}

object GithubBaseUrl {
    external fun baseUrlFromJNI(): String
}

