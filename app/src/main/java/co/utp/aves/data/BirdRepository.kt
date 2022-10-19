package co.utp.aves.data

import android.content.Context
import co.utp.aves.R
import co.utp.aves.domain.interfaces.IBirdRepository
import co.utp.aves.presentation.model.AboutUs
import co.utp.aves.presentation.model.Ave
import co.utp.aves.presentation.model.JsonAboutUs
import co.utp.aves.presentation.model.JsonAve
import co.utp.aves.utils.fromJson
import co.utp.aves.utils.getJson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class BirdRepository @Inject constructor(@ApplicationContext private val context: Context)
    : IBirdRepository {

    private val jsonAve by lazy {
    fromJson(getJson(R.string.json_birds, context), JsonAve::class.java)}

    private val jsonAboutUs by lazy {
        fromJson(getJson(R.string.json_about_us, context), JsonAboutUs::class.java)
    }


    override suspend fun getListBirds(): List<Ave> {
        return jsonAve.Aves
    }

    override suspend fun findBird(idAve: String?): Ave {
        return jsonAve.Aves.find { it.Codigo == idAve }!!
    }

    override suspend fun getAboutUs(): List<AboutUs> {
        return jsonAboutUs.AboutUs
    }
}