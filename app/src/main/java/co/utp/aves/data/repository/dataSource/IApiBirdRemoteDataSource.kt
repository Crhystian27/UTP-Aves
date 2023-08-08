package co.utp.aves.data.repository.dataSource

import co.utp.aves.data.remote.model.BirdResponse
import retrofit2.Response
import java.io.File

interface IApiBirdRemoteDataSource {

    suspend fun uploadImage(file: File) : Response<BirdResponse>
}