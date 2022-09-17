package com.gultendogan.weightlyapp.ui.add

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.data.local.WeightDao
import com.gultendogan.weightlyapp.data.local.WeightEntity
import com.gultendogan.weightlyapp.domain.mapper.WeightEntityMapper
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.domain.usecase.SaveOrUpdateWeight
import com.gultendogan.weightlyapp.ui.home.HomeViewModel
import com.gultendogan.weightlyapp.utils.extensions.endOfDay
import com.gultendogan.weightlyapp.utils.extensions.startOfDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class AddWeightViewModel @Inject constructor(
    private val weightDao: WeightDao,
    private val saveOrUpdateWeight: SaveOrUpdateWeight
) : ViewModel() {

    sealed class Event {
        object PopBackStack : Event()
        data class ShowToast(@StringRes val textResId: Int) : Event()
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun saveOrUpdateWeight(weight: String, note: String, emoji: String, date: Date) {
        viewModelScope.launch(Dispatchers.IO) {

            when {
                weight.isBlank() -> {
                    eventChannel.send(Event.ShowToast(R.string.alert_blank_weight))
                }
                else -> {
                    saveOrUpdateWeight.invoke(
                        weight = weight,
                        note = note,
                        emoji = emoji,
                        date = date
                    )
                    eventChannel.send(Event.PopBackStack)
                }
            }
        }
    }

    fun fetchDate(date: Date) {
        viewModelScope.launch(Dispatchers.IO) {
            val weightList = weightDao.fetchBy(
                startDate = date.startOfDay(),
                endDate = date.endOfDay()
            )
            val uiModel = WeightEntityMapper.map(weightList.firstOrNull())
            _uiState.update {
                it.copy(currentWeight = uiModel)
            }
        }
    }

    data class UiState(
        var currentWeight: WeightUIModel? = null
    )

}