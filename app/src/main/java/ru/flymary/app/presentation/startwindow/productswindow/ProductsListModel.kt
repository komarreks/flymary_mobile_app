package ru.flymary.app.presentation.startwindow.productswindow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.flymary.app.model.NodeDTO
import ru.flymary.app.model.ProductDTO
import ru.flymary.app.remoteserver.RemoteServer

class ProductsListModel(var nodeId: String): ViewModel() {

    private val _products = MutableStateFlow<List<ProductDTO>>(listOf())
    val products = _products.asStateFlow()

    private val _filters = MutableStateFlow<List<NodeDTO>>(listOf())
    val filters = _filters.asStateFlow()

    init {
        updateProducts(nodeId)
    }

    fun filterProducts(filters: List<String>){
        viewModelScope.launch {
            try {
                val productsFromServer = RemoteServer.FWS.getProducts(nodeId)

                remapImages(productsFromServer)

                if (filters.isEmpty()){
                    _products.value = productsFromServer
                }else{
                    _products.value = productsFromServer.filter { filters.contains(it.filterNode) }.toList()
                }
            }catch (ex: Exception){
                _products.value = listOf()
            }
        }
    }

    private fun remapImages(productDTOs: List<ProductDTO>){
        for (productDTO in productDTOs){
            for (charac in productDTO.characTDOs){

                val links: MutableList<String> = mutableListOf()

                charac.imageUrl.map {
                    links.add(RemoteServer.imageApi + it)
                }

                charac.imageUrl = links
            }
        }
    }

    private fun updateProducts(nodeId: String){
        viewModelScope.launch {
            try {
                _filters.value = RemoteServer.FWS.getFilters(nodeId)

                val productsFromServer = RemoteServer.FWS.getProducts(nodeId)

                remapImages(productsFromServer)

                _products.value = productsFromServer
            }catch (ex: Exception){
                _products.value = listOf()
            }
        }
    }
}