package co.utp.aves.utils

import android.content.Context
import androidx.annotation.StringRes
import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset


fun <T> fromJson(json: String, classOfT: Class<T>): T {
    return try {
        Gson().fromJson(json, classOfT)
    } catch (error: Exception) {
        "$error".LogError()
        Gson().fromJson("{}", classOfT)

    }
}

fun getJson(@StringRes fileNameString: Int, context: Context): String {
    var json = ""
    try {
        val inputStream = context.assets.open("json/" + context.getString(fileNameString) + ".json")
        json = inputStream.readTextAndClose()
    } catch (ex: IOException) {
        ex.printStackTrace()
        "$ex".LogError()
        return json
    }

    return json

}

fun InputStream.readTextAndClose(charset: Charset = Charsets.UTF_8): String {
    return this.bufferedReader(charset).use { it.readText() }
}