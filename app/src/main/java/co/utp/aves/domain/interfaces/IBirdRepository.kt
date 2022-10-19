package co.utp.aves.domain.interfaces

import co.utp.aves.presentation.model.AboutUs
import co.utp.aves.presentation.model.Ave

interface IBirdRepository {

    suspend fun getListBirds(): List<Ave>

    suspend fun findBird(idAve: String?) : Ave

    suspend fun getAboutUs(): List<AboutUs>
}