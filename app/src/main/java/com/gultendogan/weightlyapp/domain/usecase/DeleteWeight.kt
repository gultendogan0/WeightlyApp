package com.gultendogan.weightlyapp.domain.usecase

import com.gultendogan.weightlyapp.data.local.WeightDao
import com.gultendogan.weightlyapp.utils.extensions.endOfDay
import com.gultendogan.weightlyapp.utils.extensions.startOfDay
import java.util.*
import javax.inject.Inject

class DeleteWeight @Inject constructor(private val weightDao: WeightDao) {

    suspend operator fun invoke(date: Date) {
        val weightList = weightDao.fetchBy(
            startDate = date.startOfDay(),
            endDate = date.endOfDay()
        )
        weightList.firstOrNull()?.run {
            weightDao.delete(this)
        }
    }

}