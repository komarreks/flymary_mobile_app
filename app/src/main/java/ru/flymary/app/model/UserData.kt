package ru.flymary.app.model

import java.lang.StringBuilder

data class UserData(
    val name: String,
    val phones: List<String>,
    val addresses: List<String>
) {

    fun getCollection(list: List<String>, addedSimbol: String = ""): String{
        val sb = StringBuilder()
        for (element in list){
            sb.append("$addedSimbol$element").append("\n")
        }

        return sb.toString()
    }
}