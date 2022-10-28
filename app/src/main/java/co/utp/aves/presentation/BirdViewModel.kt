package co.utp.aves.presentation

import co.utp.aves.base.BaseViewModel
import co.utp.aves.domain.usescases.FindBirdUseCase
import co.utp.aves.domain.usescases.GetAboutUsUseCase
import co.utp.aves.domain.usescases.GetBirdUseCase
import co.utp.aves.presentation.model.Ave
import co.utp.aves.utils.LogError
import dagger.hilt.android.lifecycle.HiltViewModel
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer


@HiltViewModel
class BirdViewModel @Inject constructor(
    private val findBirdUseCase: FindBirdUseCase,
    private val getBirdUseCase: GetBirdUseCase,
    private val getAboutUs: GetAboutUsUseCase
) : BaseViewModel<BirdEvent>() {

    private var allBirds: List<Ave> = emptyList()

    fun getAboutUs() {
        executeUseCase(
            { getAboutUs.execute() },
            { result ->
                result.also { ls ->
                    _event.value = BirdEvent.ListAboutUs(ls)
                }
            },
            { error -> "$error".LogError() }
        )
    }

    fun getBirds() {
        executeUseCase(
            { getBirdUseCase.execute() },
            { result ->
                result.also { ls ->
                    allBirds = ls
                    _event.value = BirdEvent.ListBird(ls)
                }
            },
            { error -> "$error".LogError() }
        )
    }

    fun findBird(idBird: String) {
        executeUseCase(
            { findBirdUseCase.execute(FindBirdUseCase.Params(idBird)) },
            { result -> _event.value = BirdEvent.FindBird(result) },
            { error -> "$error".LogError() }
        )
    }

    fun searchBird(searchText: String?, filterList: List<Ave>?) {
        if (searchText.isNullOrEmpty()) {
            _event.value = BirdEvent.ListBird(allBirds)

        } else {
            val birds = filterList ?: allBirds
            searchText.lowercase().also { search ->
                _event.value = BirdEvent.ListBird(
                    birds.filter {
                        StringUtils.stripAccents(it.Nombre_Comun).lowercase().contains(search)||
                                it.Nombre_Cientifico.lowercase().contains(search)
                    }
                )
            }
        }
    }
}