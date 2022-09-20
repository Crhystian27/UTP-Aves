package co.utp.aves.utils

import android.util.Log

fun String.LogWarning() {
    Log.w("Warning: 🚫🚫🚫🚫🚫🚫", this)
}

fun String.LogDebug() {
    Log.d("Debug: 😎😎😎😎😎😎", this)
}

fun String.LogVerbose() {
    Log.v("Verbose: 👀👀👀👀👀👀", this)
}

fun String.LogInfo() {
    Log.i("Info: 😋😋😋😋😋😋", this)
}

fun String.LogError() {
    Log.e("Error: ❌❌❌❌❌❌", this)
}