package ru.flymary.app.presentation.startwindow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.flymary.app.model.CatalogDTO
import ru.flymary.app.remoteserver.RemoteServer

class StartModel: ViewModel() {

    private val _imagesForBanner = MutableStateFlow<List<String>>(listOf())
    val imagesForBanner = _imagesForBanner.asStateFlow()

    private val _catalogs = MutableStateFlow<List<CatalogDTO>>(listOf())
    val catalogs = _catalogs.asStateFlow()

    init {
        updateMainBanner()
        updateCatalogs()
    }

    fun updateMainBanner(){
        viewModelScope.launch {
            var imageStringList: List<String> = listOf()

            try {
                imageStringList = RemoteServer.FWS.getMainBanner()
            }catch (ex: Exception){
                imageStringList = listOf("empty")
            }

            val links: MutableList<String> = mutableListOf()

            for (link in imageStringList){
                links.add(RemoteServer.imageApi + link)
            }

            _imagesForBanner.value = links
        }
    }

    fun updateCatalogs(){
        viewModelScope.launch {
            try {
                _catalogs.value = RemoteServer.FWS.getCatalogs()
            }catch (ex: Exception){
                _catalogs.value = listOf()
            }
        }
    }
}