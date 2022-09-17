package com.gultendogan.weightlyapp.domain.usecase

import com.gultendogan.weightlyapp.data.local.WeightDao
import com.gultendogan.weightlyapp.data.local.WeightEntity
import com.gultendogan.weightlyapp.utils.extensions.endOfDay
import com.gultendogan.weightlyapp.utils.extensions.startOfDay
import java.util.*
import javax.inject.Inject

class SaveOrUpdateWeight @Inject constructor(private val weightDao: WeightDao) {

    suspend operator fun invoke(weight: String, note: String, emoji: String, date: Date) {
        val weightList = weightDao.fetchBy(
            startDate = date.startOfDay(),
            endDate = date.endOfDay()
        )
        if (weightList.isEmpty()) {
            weightDao.save(
                WeightEntity(
                    timestamp = date,
                    value = weight.toFloat(),
                    emoji = emoji,
                    note = note
                )
            )
        } else {
            weightDao.update(
                WeightEntity(
                    uid = weightList.first().uid,
                    timestamp = date,
                    value = weight.toFloat(),
                    emoji = emoji,
                    note = note
                )
            )
        }
    }

}