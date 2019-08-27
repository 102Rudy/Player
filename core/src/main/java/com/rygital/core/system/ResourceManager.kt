package com.rygital.core.system

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResourceManager {

    fun getString(@StringRes stringId: Int): String

    @ColorInt
    fun getColor(@ColorRes colorId: Int): Int
}