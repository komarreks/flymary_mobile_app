package ru.flymary.app.presentation.startwindow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.flymary.app.remoteserver.RemoteServer

class StartModel: ViewModel() {

    private val _imagesForBanner = MutableStateFlow<List<String>>(listOf())
    val imagesForBanner = _imagesForBanner.asStateFlow()

    init {
        updateMainBanner()
    }

    fun updateMainBanner(){
        viewModelScope.launch {
            val imageStringList: MutableList<String> = RemoteServer.FWS.getMainBanner().toMutableList()

            val links: MutableList<String> = mutableListOf()

            for (link in imageStringList){
                links.add(RemoteServer.imageApi + link)
            }

            _imagesForBanner.value = links
        }

    }
}