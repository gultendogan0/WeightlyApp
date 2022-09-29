package com.gultendogan.weightlyapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarEntry
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
class HomeViewModel @Inject constructor(
    private var weightRepository: WeightRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        getWeightHistories()
    }

    private fun getWeightHistories() = viewModelScope.launch(Dispatchers.IO) {
        weightRepository.invoke().collectLatest { weightHistories ->
            _uiState.update {
                it.copy(histories = weightHistories,
                    reversedHistories = weightHistories.asReversed() ,
                    barEntries = weightHistories.mapIndexed { index, weight ->
                        BarEntry(index.toFloat(), weight?.value.orZero())
                    },
                    shouldShowEmptyView = weightHistories.isEmpty())
            }
        }
    }

    data class UiState(
        var histories: List<WeightUIModel?> = emptyList(),
        var reversedHistories: List<WeightUIModel?> = emptyList(),
        var barEntries: List<BarEntry> = emptyList(),
        var shouldShowEmptyView: Boolean = false
    )

}