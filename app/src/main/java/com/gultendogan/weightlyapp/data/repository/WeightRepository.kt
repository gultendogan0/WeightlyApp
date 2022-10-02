package com.gultendogan.weightlyapp.data.repository

import com.gultendogan.weightlyapp.data.local.WeightDao
import javax.inject.Inject

class WeightRepository @Inject constructor(
    private val dbDao: WeightDao
) {

    fun getAllWeights() = dbDao.getAllWeights()

}