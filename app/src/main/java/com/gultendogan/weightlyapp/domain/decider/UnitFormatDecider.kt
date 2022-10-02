package com.gultendogan.weightlyapp.domain.decider

import android.content.Context
import com.orhanobut.hawk.Hawk
import com.gultendogan.weightlyapp.R
import com.gultendogan.weightlyapp.uicomponents.MeasureUnit
import com.gultendogan.weightlyapp.utils.Constants
import com.gultendogan.weightlyapp.utils.extensions.orZero
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class UnitFormatDecider @Inject constructor(@ApplicationContext val context: Context) {

    operator fun invoke(value: Float?): String {
        return if (MeasureUnit.findValue(Hawk.get(Constants.Prefs.KEY_GOAL_WEIGHT_UNIT)) == MeasureUnit.KG) {
            context.getString(R.string.kg_format, value.orZero())
        } else {
            context.getString(R.string.lb_format, value.orZero())
        }
    }
}