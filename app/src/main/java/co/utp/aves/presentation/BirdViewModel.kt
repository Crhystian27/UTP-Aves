package co.utp.aves.presentation

import co.utp.aves.base.BaseViewModel
import co.utp.aves.domain.usescases.FindBirdUseCase
import co.utp.aves.domain.usescases.GetAboutUsUseCase
import co.utp.aves.domain.usescases.GetBirdUseCase
import co.utp.aves.utils.LogError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BirdViewModel @Inject constructor(
    private val findBirdUseCase: FindBirdUseCase,
    private val getBirdUseCase: GetBirdUseCase,
    private val getAboutUs: GetAboutUsUseCase
): BaseViewModel<BirdEvent>() {

    fun getAboutUs(){
        executeUseCase(
            { getAboutUs.execute() },
            { result -> result.also { ls ->
                _event.value = BirdEvent.ListAboutUs(ls) }},
            {error -> "$error".LogError()}
        )
    }

    fun getBirds(){
        executeUseCase(
            { getBirdUseCase.execute() },
            { result -> result.also { ls ->
                _event.value = BirdEvent.ListBird(ls) } },
            { error -> "$error".LogError()}
        )
    }

    fun findBird(idBird: String){
        executeUseCase(
            { findBirdUseCase.execute(FindBirdUseCase.Params(idBird)) },
            { result ->  _event.value = BirdEvent.FindBird(result) },
            { error -> "$error".LogError()}
        )
    }
}