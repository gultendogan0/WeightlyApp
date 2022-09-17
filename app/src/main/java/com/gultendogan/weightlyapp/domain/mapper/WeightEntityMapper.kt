package com.gultendogan.weightlyapp.domain.mapper

import com.gultendogan.weightlyapp.data.local.WeightEntity
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.utils.extensions.EMPTY
import com.gultendogan.weightlyapp.utils.extensions.orZero
import java.util.*

object WeightEntityMapper {

    fun map(entity: WeightEntity?): WeightUIModel? {

        if (entity == null)
            return null

        return WeightUIModel(
            uid = entity.uid.orZero,
            valueText = if (entity.value != null) {
                entity.value.toString()
            } else {
                String.EMPTY
            },
            value = entity.value.orZero(),
            emoji = entity.emoji.orEmpty(),
            note = entity.note.orEmpty(),
            date = entity.timestamp ?: Date()
        )
    }
}