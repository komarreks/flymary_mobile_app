package ru.flymary.app.model.products

import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@JsonClass(generateAdapter = true)
data class CharacDTO(
    val id: String,
    val productId:String,
    val name: String,
    var imageUrl: MutableList<String>,
    val price: Double,
    val count:Double
) {
}