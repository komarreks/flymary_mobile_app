package ru.flymary.app.presentation.startwindow.productonewindow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.flymary.app.model.products.ProductDTO
import ru.flymary.app.remoteserver.RemoteServer

class ProductModel(private val productId:String):ViewModel() {

    private var _productDTO = MutableStateFlow<ProductDTO?>(null)

    val productDTO = _productDTO.asStateFlow()

    init {
        getProduct(productId)
    }

    fun getProduct(id: String){
        viewModelScope.launch {
            val productDTO = RemoteServer.FWS.getProduct(id)
            remapImages(productDTO)
            _productDTO.value = productDTO
            val a = 0
        }
    }

    private fun remapImages(productDTO: ProductDTO){
        val links: MutableList<String> = mutableListOf()
        productDTO.imageUrl.map {
            links.add(RemoteServer.imageApi + it)
        }
        productDTO.imageUrl = links

        for (charac in productDTO.characTDOs){

            val links: MutableList<String> = mutableListOf()

            charac.imageUrl.map {
                links.add(RemoteServer.imageApi + it)
            }

            charac.imageUrl = links
        }

    }
}