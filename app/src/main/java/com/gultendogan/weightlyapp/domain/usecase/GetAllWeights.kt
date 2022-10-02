package com.gultendogan.weightlyapp.domain.usecase

import com.gultendogan.weightlyapp.data.local.WeightEntity
import com.gultendogan.weightlyapp.data.repository.WeightRepository
import com.gultendogan.weightlyapp.domain.mapper.WeightEntityMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllWeights @Inject constructor(
    private val repository: WeightRepository,
    private val mapper: WeightEntityMapper
) {
    operator fun invoke() = repository.getAllWeights().map { weightList ->
        weightList.mapIndexed { index, weightEntity ->
            var previousEntity: WeightEntity? = null
            val previousIndex = index + 1
            if (previousIndex < weightList.size && previousIndex >= 0) {
                previousEntity = weightList[previousIndex]
            }
            mapper.map(entity = weightEntity, previousEntity = previousEntity)
        }.reversed()
    }
}