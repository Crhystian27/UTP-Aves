package co.utp.aves.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ave(
    var Alimentos: List<String>,
    var Codigo: String,
    var Distribucion: String,
    var Familia: String,
    var Imagen_Ave: String,
    var Nombre_Cientifico: String,
    var Nombre_Comun: String,
    var Nombre_Ingles: String,
    var Orden: String,
    var Sonidos: List<String>,
    var Tamano: String,
    var Vocalizacion: String
): Parcelable

fun equals(oldItem: Ave, newItem: Ave): Boolean =
    oldItem.Alimentos == newItem.Alimentos && oldItem.Codigo == newItem.Codigo &&
            oldItem.Imagen_Ave == newItem.Imagen_Ave && oldItem.Nombre_Cientifico == newItem.Nombre_Cientifico &&
            oldItem.Sonidos == newItem.Sonidos