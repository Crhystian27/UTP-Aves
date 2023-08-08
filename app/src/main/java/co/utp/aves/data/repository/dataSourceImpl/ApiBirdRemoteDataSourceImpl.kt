package co.utp.aves.data.repository.dataSourceImpl

import co.utp.aves.data.remote.model.BirdResponse
import co.utp.aves.data.remote.ApiBird
import co.utp.aves.data.repository.dataSource.IApiBirdRemoteDataSource
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class ApiBirdRemoteDataSourceImpl @Inject constructor(
    private val apiBird: ApiBird
) : IApiBirdRemoteDataSource {

    override suspend fun uploadImage(file: File): Response<BirdResponse> =
        apiBird.uploadImage(
            image = MultipartBody.Part
                .createFormData(
                    "file",
                    file.name,
                    file.asRequestBody()
                )
        )

}