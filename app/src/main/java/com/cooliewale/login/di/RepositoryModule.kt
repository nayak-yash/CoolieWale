package com.cooliewale.login.di

import com.cooliewale.login.repository.AuthRepository
import com.cooliewale.login.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesFirebaseAuthRepository(repo: AuthRepositoryImpl): AuthRepository
}