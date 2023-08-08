package co.utp.aves.domain.usescases

import co.utp.aves.base.BaseUseCaseNoParams
import co.utp.aves.domain.interfaces.IBirdRepository
import co.utp.aves.presentation.model.AboutUs
import javax.inject.Inject

class GetAboutUsUseCase @Inject constructor(private val repository: IBirdRepository) :
    BaseUseCaseNoParams<List<AboutUs>>() {

    override suspend fun execute(): List<AboutUs> =
        repository.getAboutUs()
}