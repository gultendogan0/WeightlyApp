package com.gultendogan.weightlyapp.data.repository

import com.gultendogan.weightlyapp.data.local.AppDatabase
import com.gultendogan.weightlyapp.data.local.WeightDao
import com.gultendogan.weightlyapp.domain.mapper.WeightEntityMapper
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeightRepository @Inject constructor(
    private val dbDao: WeightDao
) {

    operator fun invoke() = dbDao.getDbAll().map { weightList ->
        weightList.map {
            WeightEntityMapper.map(it)

        }.reversed()

    }

}