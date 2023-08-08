package co.utp.aves.presentation

import co.utp.aves.base.BaseViewModel
import co.utp.aves.domain.usescases.*
import co.utp.aves.presentation.model.Ave
import co.utp.aves.utils.LogError
import dagger.hilt.android.lifecycle.HiltViewModel
import org.apache.commons.lang3.StringUtils
import java.io.File
import javax.inject.Inject


@HiltViewModel
class BirdViewModel @Inject constructor(
    private val findBirdUseCase: FindBirdUseCase,
    private val getBirdUseCase: GetBirdUseCase,
    private val getAboutUsUseCase: GetAboutUsUseCase,
    private val getAboutUsDependenciesUseCase: GetAboutUsDependenciesUseCase,
    private val uploadImageUseCase: UploadImageUseCase
) : BaseViewModel<BirdEvent>() {

    private var allBirds: List<Ave> = emptyList()

    fun uploadImage(file: File) {
        executeUseCase(
            { uploadImageUseCase.execute(UploadImageUseCase.Params(file)) },
            { result -> _event.value = BirdEvent.IdBird(result) },
            { error -> "$error".LogError() }
        )
    }

    fun getAboutUs() {
        executeUseCase(
            { getAboutUsUseCase.execute() },
            { result ->
                result.also { ls ->
                    _event.value = BirdEvent.ListAboutUs(ls)
                }
            },
            { error -> "$error".LogError() }
        )
    }

    fun getAboutUsDependencies() {
        executeUseCase(
            { getAboutUsDependenciesUseCase.execute() },
            { result ->
                result.also { ls ->
                    _event.value = BirdEvent.ListAboutUsDependencies(ls)
                }
            },
            { error-> "$error".LogError() }
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
                        StringUtils.stripAccents(it.Nombre_Comun).lowercase().contains(search) ||
                                it.Nombre_Cientifico.lowercase().contains(search)
                    }
                )
            }
        }
    }
}