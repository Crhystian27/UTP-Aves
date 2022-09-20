package co.utp.aves.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.utp.aves.utils.LogError
import kotlinx.coroutines.launch

abstract class BaseViewModel<T>: ViewModel() {

    protected val _event by lazy { MutableLiveData<T>() }
    val event: LiveData<T> = _event

    protected val tag = this::class.java.name

    fun <T> executeUseCase(
        useCase: suspend () -> T,
        success: (T) -> Unit,
        error: (Throwable) -> Unit = {"$tag -> ${useCase.javaClass.name}".LogError()}
    ) {
        viewModelScope.launch {
            try {
                useCase().also { result ->
                    success(result)
                }
            } catch (t: Throwable) {
                error(t)
            }
        }
    }

}