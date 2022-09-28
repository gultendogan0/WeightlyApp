package com.gultendogan.weightlyapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarEntry
import com.gultendogan.weightlyapp.data.local.WeightDao
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.ui.home.HomeViewModel
import com.gultendogan.weightlyapp.utils.extensions.endOfDay
import com.gultendogan.weightlyapp.utils.extensions.startOfDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    var weightDao: WeightDao
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun fetchInsights() {
        viewModelScope.launch(Dispatchers.IO) {
            val averageWeight = weightDao.getAverage()
            val maxWeight = weightDao.getMax()
            val minWeight = weightDao.getMin()
            _uiState.update {
                it.copy(
                    averageWeight = "$averageWeight",
                    minWeight = "$minWeight",
                    maxWeight = "$maxWeight"
                )
            }
        }

    }

    data class UiState(
        var maxWeight: String? = null,
        var minWeight: String? = null,
        var averageWeight: String? = null
    )


}