package ru.flymary.app.presentation.startwindow.catalogwindow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.flymary.app.model.catalog.NodeDTO
import ru.flymary.app.remoteserver.RemoteServer

class CatalogModel(
    var catalodId: String,
): ViewModel() {

    private val _nodes = MutableStateFlow<List<NodeDTO>>(listOf())
    val nodes = _nodes.asStateFlow()

    fun setCatalogId(c: String){
        catalodId = c
    }

    init {
        updateNodes(catalodId)
    }

    private fun updateNodes(catalodId: String){
        viewModelScope.launch {
            try {
                _nodes.value = RemoteServer.FWS.getRoot(catalodId)
            }catch (ex: Exception){
                _nodes.value = listOf()
            }
        }
    }
}