package co.utp.aves.data.remote

import co.utp.aves.BuildConfig
import co.utp.aves.data.remote.model.BirdResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiBird  {

    @Multipart
    @POST(BuildConfig.BASE_URL+"predict")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part
    ): Response<BirdResponse>
}