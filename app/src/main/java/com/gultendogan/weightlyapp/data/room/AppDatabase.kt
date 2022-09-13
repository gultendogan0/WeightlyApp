package com.gultendogan.weightlyapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import java.util.*

@Database(
    entities = [WeightEntity::class],
    version=1,
    exportSchema=false
)

abstract class AppDatabase : RoomDatabase(){
    abstract fun weightDao():WeightDao
}