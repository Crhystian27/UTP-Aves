package co.utp.aves.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AboutUs(
    var Photo: String,
    var Email: String,
    var LinkedIn: String,
    var Name: String
): Parcelable


fun equals(oldItem: AboutUs, newItem: AboutUs): Boolean =
    oldItem.Photo == newItem.Photo && oldItem.Email == newItem.Email &&
            oldItem.LinkedIn == newItem.LinkedIn && oldItem.Name == newItem.Name