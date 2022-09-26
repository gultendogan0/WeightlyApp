package com.gultendogan.weightlyapp.ui.home.chart

import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.utils.extensions.orZero

class WeightValueFormatter(var histories: List<WeightUIModel?>) : ValueFormatter() {
    override fun getBarLabel(barEntry: BarEntry?): String {
        val history = histories[barEntry?.x?.toInt().orZero]
        return "${history?.emoji} ${history?.valueText}"
    }

}