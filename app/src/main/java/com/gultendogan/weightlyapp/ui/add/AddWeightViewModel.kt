package com.gultendogan.weightlyapp.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gultendogan.weightlyapp.data.local.AppDatabase
import com.gultendogan.weightlyapp.data.local.WeightEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddWeightViewModel @Inject constructor(
    private val appDatabase: AppDatabase
) : ViewModel() {

    fun addWeight(weight: String, note: String, date: Date) {
        viewModelScope.launch(Dispatchers.IO) {
            appDatabase.weightDao()
                .insert(WeightEntity(
                    timestamp = date,
                    value = weight.toFloat(),
                    emoji = "E",
                    note = note
                ))
        }
    }

}