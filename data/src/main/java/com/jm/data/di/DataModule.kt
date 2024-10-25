package com.jm.data.di

import android.content.Context
import com.jm.data.repository.UserRepositoryImpl
import com.jm.data.service.Api
import com.jm.data.service.UserClient
import com.jm.data.service.UserInterceptor
import com.jm.data.service.UserService
import com.jm.data.service.UserServiceImpl
import com.jm.data.utils.CheckInternetConnection
import com.jm.data.utils.CheckInternetConnectionImpl
import com.jm.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun providesInterceptor(): UserInterceptor {
        return UserInterceptor()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(interceptor: UserInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder().baseUrl(Api.url).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()

    }

    @Provides
    @Singleton
    fun providesUserClient(retrofit: Retrofit): UserClient {
        return retrofit.create(UserClient::class.java)
    }


    @Provides
    @Singleton
    fun providesUserService(
        userClient: UserClient,
        checkInternetConnection: CheckInternetConnection
    ): UserService {
        return UserServiceImpl(userClient, checkInternetConnection)
    }

    @Provides
    @Singleton
    fun providesUserRepository(userService: UserService): UserRepository {
        return UserRepositoryImpl(userService)
    }

    @Provides
    @Singleton
    fun providesCheckInternetConnection(@ApplicationContext context: Context): CheckInternetConnection {
        return CheckInternetConnectionImpl(context)
    }
}