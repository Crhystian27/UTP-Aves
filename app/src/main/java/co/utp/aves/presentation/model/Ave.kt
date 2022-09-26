package co.utp.aves.presentation.model

data class Ave(
    val Alimentos: List<String>,
    val Codigo: String,
    val Distribucion: String,
    val Familia: String,
    val Imagen_Ave: String,
    val Nombre_Cientifico: String,
    val Nombre_Comun: String,
    val Nombre_Ingles: String,
    val Orden: String,
    val Sonidos: List<String>,
    val Tamano: String,
    val Vocalizacion: String
)