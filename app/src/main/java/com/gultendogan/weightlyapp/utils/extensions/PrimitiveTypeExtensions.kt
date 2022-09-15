package com.gultendogan.weightlyapp.utils.extensions

fun Float?.orZero() = this ?: 0.0f

val Int?.orZero: Int get() = this ?: 0