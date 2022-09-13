package com.gultendogan.weightlyapp.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WeightDao {
    @Query("SELECT * FROM weight")
    fun getAll(): List<WeightEntity>

    @Query("SELECT * FROM weight WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<WeightEntity>

    @Insert
    fun insertAll(vararg users: WeightEntity)

    @Insert
    fun insert(weight: WeightEntity)

    @Delete
    fun delete(user: WeightEntity)
}