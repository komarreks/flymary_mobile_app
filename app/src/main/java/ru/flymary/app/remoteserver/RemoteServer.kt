package ru.flymary.app.remoteserver

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.flymary.app.model.basket.Basket
import ru.flymary.app.model.basket.BasketString
import ru.flymary.app.model.catalog.CatalogDTO
import ru.flymary.app.model.catalog.NodeDTO
import ru.flymary.app.model.user.PhoneWithCode
import ru.flymary.app.model.products.ProductDTO
import ru.flymary.app.model.user.Uid
import ru.flymary.app.model.user.UserData

const val BASE_URL = "http://10.24.10.10:8080"

class RemoteServer {
    companion object{
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().addLast(
                KotlinJsonAdapterFactory()
            ).build()))
            .build()

        var FWS: FLYMARY_WEB_SERVER = retrofit.create(FLYMARY_WEB_SERVER::class.java)

        val serverPath = BASE_URL

        val imageApi = "$BASE_URL/api/image/"
        val catalogApi = "$BASE_URL/api/catalog/"
        val nodeApi = "$BASE_URL/api/node/"
    }
}

interface FLYMARY_WEB_SERVER{

    @GET(value = "/api/banner/main/app")
    suspend fun getMainBanner(): List<String>

    @GET(value = "api/catalog/all")
    suspend fun getCatalogs(): List<CatalogDTO>

    @GET(value = "api/node/root")
    suspend fun getRoot(@Query("catalog") id: String): List<NodeDTO>

    @GET(value = "api/node/goods")
    suspend fun getProducts(@Query("node") node: String): List<ProductDTO>

    @GET(value = "api/node/filters")
    suspend fun getFilters(@Query("node") node: String): List<NodeDTO>

    @GET("api/goods/{id}")
    suspend fun getProduct(@Path("id") id: String): ProductDTO

    //@Headers("Content-Type: application/json")
    @POST("/api/users/getcode")
    suspend fun getCode(@Body body: String): Int

    //@Headers("Content-Type: application/json")
    @POST(value = "api/users/autor")
    suspend fun autor(@Body phoneWithCode: PhoneWithCode): Uid

    @POST(value = "api/users/userdata")
    suspend fun userData(@Body userId: Uid): UserData

    @GET(value = "api/orders/basket/{uid}")
    suspend fun getBasket(@Path("uid") uid: String): List<BasketString>
}