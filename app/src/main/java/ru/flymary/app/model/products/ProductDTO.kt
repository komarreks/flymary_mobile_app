package ru.flymary.app.model.products

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@JsonClass(generateAdapter = true)
data class ProductDTO(
    val id: String,
    val name: String,
    val price: Double,
    val count:Double,
    val filterNode: String,
    var imageUrl: List<String>,
    val characTDOs: List<CharacDTO>
) {
}