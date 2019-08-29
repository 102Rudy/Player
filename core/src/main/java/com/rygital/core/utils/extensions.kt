package com.rygital.core.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.rygital.core.di.ApplicationComponentProvider

inline val FragmentActivity.componentProvider: ApplicationComponentProvider
    get() = (applicationContext as ApplicationComponentProvider)

inline val Fragment.componentProvider: ApplicationComponentProvider
    get() = (requireActivity().componentProvider)