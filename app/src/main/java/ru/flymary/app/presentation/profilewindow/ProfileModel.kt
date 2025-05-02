package ru.flymary.app.presentation.profilewindow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.flymary.app.model.PhoneWithCode
import ru.flymary.app.model.Uid
import ru.flymary.app.model.UserData
import ru.flymary.app.remoteserver.RemoteServer

class ProfileModel(): ViewModel() {

    private val _userID = MutableStateFlow<String>("")
    var userID = _userID.asStateFlow()

    private val _answerCode = MutableStateFlow<Int>(0)
    var answerCode = _answerCode.asStateFlow()

    private val _userData = MutableStateFlow<UserData?>(null)
    var userData = _userData.asStateFlow()

    init {
        setDefaultCode()
    }

    fun getAutorizeCode(phone: String){
        viewModelScope.launch {
            _answerCode.value = RemoteServer.FWS.getCode(phone)
        }
    }

    fun setDefaultCode(){
        _answerCode.value = 0
    }

    fun autor(phoneWithCode: PhoneWithCode){
        viewModelScope.launch {
            try {
                val uid = RemoteServer.FWS.autor(phoneWithCode)

                _userID.value = uid.uid
            }catch (ex: Exception){
                println(ex.stackTrace)
            }

        }
    }

    fun getUserData(userId: String){
        viewModelScope.launch {
            try {
                val uid = Uid(userId)
                val userDataFromServer = RemoteServer.FWS.userData(uid)

                _userData.value = userDataFromServer
            }catch (ex: Exception){
                println(ex.printStackTrace())
            }
        }
    }
}