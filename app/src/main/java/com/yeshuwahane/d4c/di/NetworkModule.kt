package com.yeshuwahane.d4c.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yeshuwahane.d4c.data.api.AppApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton




@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    private const val BASE_URL = "https://api.29041989.xyz/"



    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()


    @Provides
    @Singleton
    fun provideAppApi(retrofit: Retrofit): AppApi =
        retrofit.create(AppApi::class.java)





}