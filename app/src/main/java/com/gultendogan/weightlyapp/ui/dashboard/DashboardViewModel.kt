package com.gultendogan.weightlyapp.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarEntry
import com.gultendogan.weightlyapp.data.local.WeightDao
import com.gultendogan.weightlyapp.data.repository.WeightRepository
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.utils.extensions.orZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    var weightDao: WeightDao,
    private var weightRepository: WeightRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun fetchInsights() = viewModelScope.launch(Dispatchers.IO) {

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

    fun getWeightHistories() = viewModelScope.launch(Dispatchers.IO) {
        weightRepository.invoke().collectLatest { weightHistories ->
            _uiState.update {
                it.copy(
                    histories = weightHistories,
                    barEntries = weightHistories.mapIndexed { index, weight ->
                        BarEntry(index.toFloat(), weight?.value.orZero())
                    }
                )
            }
        }

    }

    data class UiState(
        var maxWeight: String? = null,
        var minWeight: String? = null,
        var averageWeight: String? = null,
        var histories: List<WeightUIModel?> = emptyList(),
        var barEntries: List<BarEntry> = emptyList(),
    )


}