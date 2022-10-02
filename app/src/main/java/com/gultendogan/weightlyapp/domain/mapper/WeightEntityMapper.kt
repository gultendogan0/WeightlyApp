package com.gultendogan.weightlyapp.domain.mapper

import com.gultendogan.weightlyapp.data.local.WeightEntity
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.utils.extensions.orZero
import com.gultendogan.weightlyapp.utils.extensions.toFormat
import com.gultendogan.weightlyapp.domain.decider.DifferenceDecider
import com.gultendogan.weightlyapp.domain.decider.UnitFormatDecider
import java.util.*
import javax.inject.Inject

const val DATE_FORMAT_CHART = "dd MMM"

const val DEFAULT_VALUE_OF_WEIGHT_DIFFERENCE = 0.0f

class WeightEntityMapper @Inject constructor(
    private val unitFormatDecider: UnitFormatDecider,
    private val differenceDecider: DifferenceDecider
) {

    fun map(entity: WeightEntity?, previousEntity: WeightEntity? = null): WeightUIModel? {
        if (entity == null)
            return null
        val date = entity.timestamp ?: Date()
        val valueText =  entity.value?.toString().orEmpty()
        val emoji = entity.emoji.orEmpty()

        val difference: Float =
            differenceDecider.provideValue(current = entity.value, previous = previousEntity?.value)
        val differenceColor = differenceDecider.provideColor(difference)
        val differenceText = differenceDecider.provideText(difference)
        val valueWithUnit = unitFormatDecider.invoke(entity.value)

        return WeightUIModel(
            uid = entity.uid.orZero,
            value = entity.value.orZero(),
            valueText = valueText,
            valueWithUnit = valueWithUnit,
            emoji = emoji,
            note = entity.note.orEmpty(),
            date = date,
            formattedDate = date.toFormat(DATE_FORMAT_CHART),
            formattedValue = "$emoji $valueText",
            difference = differenceText,
            differenceColor = differenceColor
        )
    }
}