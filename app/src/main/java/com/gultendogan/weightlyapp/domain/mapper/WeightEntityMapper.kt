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
        return WeightUIModel(
            uid = entity.uid.orZero,
            value = entity.value.orZero(),
            valueText = if (entity.value != null) {
                entity.value.toString()
            } else {
                String.EMPTY
            },
            emoji = entity.emoji.orEmpty(),
            note = entity.note.orEmpty(),

            date = date,
            formattedDate = date.toFormat(DATE_FORMAT_CHART)
        )
    }
}