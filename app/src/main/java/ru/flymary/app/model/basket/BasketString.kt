package ru.flymary.app.model.basket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable
import ru.flymary.app.model.products.CharacDTO
import ru.flymary.app.model.products.ProductDTO
import java.math.BigDecimal

data class BasketString(
    val productId: String,
    //@Json(name = "product") val product: ProductDTO,
    val characId: String,
    //@Json(name = "charac") val charac: CharacDTO,
    val count: Double,
    val price: Double,
    val total: Double
) {
}