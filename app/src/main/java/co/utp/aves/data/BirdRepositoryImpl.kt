package co.utp.aves.data

import android.content.Context
import co.utp.aves.R
import co.utp.aves.data.remote.model.BirdResponse
import co.utp.aves.data.repository.dataSource.IApiBirdRemoteDataSource
import co.utp.aves.domain.interfaces.IBirdRepository
import co.utp.aves.presentation.model.*
import co.utp.aves.utils.fromJson
import co.utp.aves.utils.getJson
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import java.io.File
import javax.inject.Inject


class BirdRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val iApiBirdRemoteDataSource: IApiBirdRemoteDataSource
) : IBirdRepository {

    private val jsonAve by lazy {
        fromJson(getJson(R.string.json_birds, context), JsonAve::class.java)
    }

    private val jsonAboutUs by lazy {
        fromJson(getJson(R.string.json_about_us, context), JsonAboutUs::class.java)
    }

    private val jsonAboutUsDependencies by lazy {
        fromJson(getJson(R.string.json_about_us_dependencies, context), JsonAboutUsDependencies::class.java)
    }

    override suspend fun getListBirds(): List<Ave> = jsonAve.Aves

    override suspend fun findBird(idAve: String?): Ave =
        jsonAve.Aves.find { it.Nombre_Cientifico == idAve }!!

    override suspend fun getAboutUs(): List<AboutUs> = jsonAboutUs.AboutUs
    override suspend fun getAboutUsDependencies(): List<AboutUsDependencies>
        = jsonAboutUsDependencies.AboutUsDependencies

    override suspend fun uploadImage(file: File): BirdResponse =
        responseToObjectResponse(iApiBirdRemoteDataSource.uploadImage(file))


    private fun <T> responseToObjectResponse(response: Response<T>): T {
        if (response.isSuccessful) {
            response.body()?.let { return it }
        }
        throw Exception("Body is null")
    }

}