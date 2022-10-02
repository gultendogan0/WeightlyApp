package com.gultendogan.weightlyapp.ui.home.chart

import com.github.mikephil.charting.formatter.ValueFormatter
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.utils.extensions.EMPTY

class XAxisValueDateFormatter(var histories: List<WeightUIModel?>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        if (value.toInt() < histories.size) {
            val history: WeightUIModel? = histories[value.toInt()]
            return history?.formattedDate.orEmpty()
        }
        return String.EMPTY
    }
}