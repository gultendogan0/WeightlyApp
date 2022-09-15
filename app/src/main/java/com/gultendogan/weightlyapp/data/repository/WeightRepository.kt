package com.gultendogan.weightlyapp.data.repository

import com.gultendogan.weightlyapp.data.local.AppDatabase
import com.gultendogan.weightlyapp.domain.mapper.WeightEntityMapper
import javax.inject.Inject

class WeightRepository @Inject constructor(private val appDatabase : AppDatabase) {

    operator fun invoke() = appDatabase.weightDao().getAll().map {
        WeightEntityMapper.map(it)
    }


}