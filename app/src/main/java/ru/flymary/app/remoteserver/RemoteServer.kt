package ru.flymary.app.remoteserver

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.flymary.app.model.CatalogDTO
import ru.flymary.app.model.NodeDTO

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
}