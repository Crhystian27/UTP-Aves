package co.utp.aves.presentation.bird

import co.utp.aves.base.BaseViewModel
import co.utp.aves.domain.usescases.FindBirdUseCase
import co.utp.aves.domain.usescases.GetBirdUseCase
import co.utp.aves.utils.LogError
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class BirdViewModel @Inject constructor(
    private val findBirdUseCase: FindBirdUseCase,
    private val getBirdUseCase: GetBirdUseCase
): BaseViewModel<BirdEvent>() {

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