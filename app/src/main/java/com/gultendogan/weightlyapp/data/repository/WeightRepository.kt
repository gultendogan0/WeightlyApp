package com.gultendogan.weightlyapp.data.repository

import com.gultendogan.weightlyapp.data.local.AppDatabase
import com.gultendogan.weightlyapp.data.local.WeightDao
import com.gultendogan.weightlyapp.domain.mapper.WeightEntityMapper
import com.gultendogan.weightlyapp.data.local.WeightEntity
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeightRepository @Inject constructor(
    private val dbDao: WeightDao,
    private val mapper: WeightEntityMapper
) {

    operator fun invoke() = dbDao.getDbAll().map { weightList ->
        weightList.mapIndexed { index, weightEntity ->
            var previousEntity: WeightEntity? = null
            val previousIndex = index + 1
            if (previousIndex < weightList.size && previousIndex >= 0){
                previousEntity = weightList[previousIndex]
            }
            mapper.map(entity = weightEntity, previousEntity = previousEntity)

        }.reversed()

    }

}