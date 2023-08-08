package co.utp.aves.domain.usescases

import co.utp.aves.base.BaseUseCase
import co.utp.aves.domain.interfaces.IBirdRepository
import co.utp.aves.presentation.model.Ave
import javax.inject.Inject

class FindBirdUseCase @Inject constructor(private val repository: IBirdRepository) :
    BaseUseCase<FindBirdUseCase.Params, Ave>() {

    data class Params(
        val idAve: String
    )

    override suspend fun execute(params: Params): Ave =
        repository.findBird(params.idAve)

}