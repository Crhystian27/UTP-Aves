package co.utp.aves.domain.interfaces

import co.utp.aves.presentation.bird.model.Ave

interface IBirdRepository {

    suspend fun getListBirds(): List<Ave>

    suspend fun findBird(idAve: String?) : Ave
}