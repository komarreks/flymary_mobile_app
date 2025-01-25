package ru.flymary.app.model

data class ProductDTO(
    val id: String,
    val name: String,
    val price: Double,
    val filterNode: String,
    val imageUrl: List<String>
) {
}