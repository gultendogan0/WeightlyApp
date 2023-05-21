package com.gultendogan.weightlyapp.ui.settings

import androidx.lifecycle.ViewModel
import com.orhanobut.hawk.Hawk
import com.gultendogan.weightlyapp.uicomponents.MeasureUnit
import com.gultendogan.weightlyapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchPreferences()
    }

    private fun fetchPreferences() {
        val unit = Hawk.get<String>(Constants.Prefs.KEY_GOAL_WEIGHT_UNIT)
        val goalWeight = Hawk.get<Float>(Constants.Prefs.KEY_GOAL_WEIGHT)
        val shouldShowLimitLine = Hawk.get(Constants.Prefs.KEY_CHART_LIMIT_LINE, true)
        _uiState.update {
            it.copy(unit = unit, goalWeight = goalWeight, shouldShowLimitLine = shouldShowLimitLine)
        }
    }

    fun updateUnit(unit: String) {
        Hawk.put(Constants.Prefs.KEY_GOAL_WEIGHT_UNIT, MeasureUnit.findValue(unit).value)
    }

    fun updateGoalWeight(goalWeight: Float) {
        Hawk.put(Constants.Prefs.KEY_GOAL_WEIGHT, goalWeight)
    }

    fun updateLimitLine(shouldShowLimitLine: Boolean) {
        Hawk.put(Constants.Prefs.KEY_CHART_LIMIT_LINE, shouldShowLimitLine)
    }

    data class UiState(
        var unit: String? = null,
        var goalWeight: Float? = null,
        var shouldShowLimitLine: Boolean = false
    )
}