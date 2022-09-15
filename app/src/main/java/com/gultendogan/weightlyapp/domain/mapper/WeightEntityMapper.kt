package com.gultendogan.weightlyapp.domain.mapper

import com.gultendogan.weightlyapp.data.local.WeightEntity
import com.gultendogan.weightlyapp.domain.uimodel.WeightUIModel
import com.gultendogan.weightlyapp.utils.extensions.orZero

class WeightEntityMapper {

    fun map(entity: WeightEntity) : WeightUIModel{
        return WeightUIModel(
            uid = entity.uid,
            value = entity.value.orZero(),
            emoji = entity.emoji.orEmpty(),
            note = entity.note.orEmpty()
        )
    }
}