package co.utp.aves.domain.interfaces

import co.utp.aves.data.remote.model.BirdResponse
import co.utp.aves.presentation.model.AboutUs
import co.utp.aves.presentation.model.AboutUsDependencies
import co.utp.aves.presentation.model.Ave
import java.io.File

interface IBirdRepository {

    suspend fun getListBirds(): List<Ave>

    suspend fun findBird(idAve: String?) : Ave

    suspend fun getAboutUs(): List<AboutUs>

    suspend fun getAboutUsDependencies(): List<AboutUsDependencies>

    suspend fun uploadImage(file: File) : BirdResponse


}