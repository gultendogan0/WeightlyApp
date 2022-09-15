package com.gultendogan.weightlyapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gultendogan.weightlyapp.data.local.AppDatabase
import com.gultendogan.weightlyapp.data.local.WeightDao
import com.gultendogan.weightlyapp.data.local.WeightEntity
import com.gultendogan.weightlyapp.data.repository.WeightRepository
import com.gultendogan.weightlyapp.domain.mapper.WeightEntityMapper
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        getAllWeightHistory()
    }

    private fun getAllWeightHistory(){
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(histories = weightRepository())
            }
        }

    }

    data class UiState(
        var histories : List<WeightUIModel> = emptyList()
    )
}