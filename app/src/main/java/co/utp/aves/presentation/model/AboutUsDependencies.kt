package co.utp.aves.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AboutUsDependencies(
    var Name: String,
    var Url: String,
    var Image: String
): Parcelable


fun equals(oldItem: AboutUsDependencies, newItem: AboutUsDependencies): Boolean =
    oldItem.Image == newItem.Image && oldItem.Url == newItem.Url &&
            oldItem.Name == newItem.Name