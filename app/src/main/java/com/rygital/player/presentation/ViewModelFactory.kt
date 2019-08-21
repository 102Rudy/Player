package com.rygital.player.presentation

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