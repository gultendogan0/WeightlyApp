package com.gultendogan.weightlyapp.domain.mapper

import com.gultendogan.weightlyapp.data.local.WeightEntity
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.utils.extensions.EMPTY
import com.gultendogan.weightlyapp.utils.extensions.orZero
import com.gultendogan.weightlyapp.utils.extensions.toFormat
import java.util.*

const val DATE_FORMAT_CHART = "dd MMM"

object WeightEntityMapper {

    fun map(entity: WeightEntity?): WeightUIModel? {
        if (entity == null)
            return null
        val date = entity.timestamp ?: Date()
        val valueText =  entity.value?.toString().orEmpty()
        val emoji = entity.emoji.orEmpty()
        return WeightUIModel(
            uid = entity.uid.orZero,
            value = entity.value.orZero(),
            valueText = valueText,
            emoji = emoji,
            note = entity.note.orEmpty(),
            date = date,
            formattedDate = date.toFormat(DATE_FORMAT_CHART),
            formattedValue = "$emoji $valueText"
        )
    }
}