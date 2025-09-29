package dev.luisbaena.prodentclient.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.luisbaena.prodentclient.data.local.preferencias.UserPreferences
import dev.luisbaena.prodentclient.data.remote.api.AuthApiService
import dev.luisbaena.prodentclient.data.repository.AuthRepositoryImpl
import dev.luisbaena.prodentclient.domain.repository.AuthRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: AuthApiService,
        userPreferences: UserPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, userPreferences)
    }
}