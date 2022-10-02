package com.gultendogan.weightlyapp.di

import com.gultendogan.weightlyapp.data.local.WeightDao
import com.gultendogan.weightlyapp.data.repository.WeightRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule{

    @Provides
    fun provideWeightRepository(
        dbDao: WeightDao
    ):WeightRepository {
        return WeightRepository(dbDao)
    }

}