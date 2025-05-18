package ru.flymary.app.model.basket

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class Basket(
    var products: MutableList<BasketString>,
    var totalCost:Double = 0.0,
    var total: Double = 0.0
){

    fun updateTotal(){
        var cost = BigDecimal(0)
        var sum = BigDecimal(0)

        for (line in products){
            cost.plus(BigDecimal(line.count))
            sum.plus(BigDecimal(line.total))
        }

        totalCost = cost.toDouble()
        total     = cost.toDouble()
    }
}