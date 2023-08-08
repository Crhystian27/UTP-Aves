package co.utp.aves.domain.usescases

import co.utp.aves.base.BaseUseCaseNoParams
import co.utp.aves.domain.interfaces.IBirdRepository
import co.utp.aves.presentation.model.AboutUsDependencies
import javax.inject.Inject

class GetAboutUsDependenciesUseCase @Inject constructor(private val repository: IBirdRepository) :
    BaseUseCaseNoParams<List<AboutUsDependencies>>() {

    override suspend fun execute(): List<AboutUsDependencies> =
        repository.getAboutUsDependencies()
}