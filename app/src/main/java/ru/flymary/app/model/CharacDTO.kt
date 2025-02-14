package ru.flymary.app.model

data class CharacDTO(
    val id: String,
    val productId:String,
    val name: String,
    var imageUrl: MutableList<String>,
    val price: Double,
    val count:Double
) {
}