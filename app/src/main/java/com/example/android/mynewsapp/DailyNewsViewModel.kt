package com.example.android.mynewsapp

import android.app.Application
import androidx.lifecycle.*

class DailyNewsViewModel(application: Application) : AndroidViewModel(application) {

    
    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _dailyNews = MutableLiveData<List<DailyArticle>>()
    
    // The external LiveData interface to the property is immutable, so only this class can modify
    val dailyNews: LiveData<List<DailyArticle>>
        get() = _dailyNews
    
    companion object {

        /**
         * Factory for creating [DailyNewsViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::DailyNewsViewModel)
    }
    
}

/**
 * Convenience factory to handle ViewModels with one parameter.
 *
 * Make a factory:
 * ```
 * // Make a factory
 * val FACTORY = viewModelFactory(::MyViewModel)
 * ```
 *
 * Use the generated factory:
 * ```
 * ViewModelProviders.of(this, FACTORY(argument))
 *
 * ```
 *
 * @param constructor A function (A) -> T that returns an instance of the ViewModel (typically pass
 * the constructor)
 * @return a function of one argument that returns ViewModelProvider.Factory for ViewModelProviders
 */
fun <T : ViewModel, A> singleArgViewModelFactory(constructor: (A) -> T):
            (A) -> ViewModelProvider.NewInstanceFactory {
    return { arg: A ->
        object : ViewModelProvider.NewInstanceFactory() {
            @Suppress("UNCHECKED_CAST")
            override fun <V : ViewModel> create(modelClass: Class<V>): V {
                return constructor(arg) as V
            }
        }
    }
}