package com.rygital.player.system

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.rygital.core.di.ApplicationContext
import com.rygital.core.di.ApplicationScope
import com.rygital.core.system.ResourceManager
import javax.inject.Inject

@ApplicationScope
class ResourceManagerImpl @Inject constructor(@ApplicationContext private val context: Context): ResourceManager {

    override fun getString(@StringRes stringId: Int): String = context.getString(stringId)

    @ColorInt
    override fun getColor(@ColorRes colorId: Int): Int = ContextCompat.getColor(context, colorId)
}