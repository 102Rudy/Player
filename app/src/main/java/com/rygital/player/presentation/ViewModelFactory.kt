package com.rygital.player.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rygital.core.di.ApplicationScope
import com.rygital.player.di.ExplorerScope
import dagger.MapKey
import javax.inject.Inject
import javax.inject.Provider
import kotlin.reflect.KClass

//
//@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
//@Retention(AnnotationRetention.RUNTIME)
//@MapKey
//internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
//
//@ViewModelScope
//class ViewModelFactory @Inject constructor(
//        private val viewModels: Map<Class<out ViewModel>, Provider<ViewModel>>
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
//            viewModels[modelClass]?.get() as T
//}