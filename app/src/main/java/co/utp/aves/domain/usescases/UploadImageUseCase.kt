package co.utp.aves.domain.usescases

import co.utp.aves.base.BaseUseCase
import co.utp.aves.data.remote.model.BirdResponse
import co.utp.aves.domain.interfaces.IBirdRepository
import java.io.File
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(private val repository: IBirdRepository) :
    BaseUseCase<UploadImageUseCase.Params, BirdResponse>() {

    data class Params(
        val file: File
    )

    override suspend fun execute(params: Params): BirdResponse =
        repository.uploadImage(params.file)

}