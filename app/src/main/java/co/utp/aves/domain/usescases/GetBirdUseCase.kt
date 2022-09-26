package co.utp.aves.domain.usescases

import co.utp.aves.base.BaseUseCaseNoParams
import co.utp.aves.domain.interfaces.IBirdRepository
import co.utp.aves.presentation.model.Ave
import javax.inject.Inject

class GetBirdUseCase @Inject constructor(private val repository: IBirdRepository):
    BaseUseCaseNoParams<List<Ave>>() {

    override suspend fun execute(): List<Ave> {
        return repository.getListBirds()
    }
}