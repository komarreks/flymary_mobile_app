package ru.flymary.app.localstorage

import android.content.Context
import android.content.SharedPreferences

private const val STRORAGE_NAME = "localShared"

class LocalStorage(private val context: Context) {

    private var sharedPref: SharedPreferences = context.getSharedPreferences(STRORAGE_NAME,0)

    fun saveUID(uid: String){
        saveValue("UID", uid)
    }

    fun getUID():String{
        return getStringValue("UID")
    }

    private fun getStringValue(key: String):String{
        val stringValue = sharedPref.getString(key, "")

        return if (stringValue!=null) stringValue
        else ""
    }

    private fun saveValue(key:String, value:String){
        val editor = sharedPref.edit()
        editor.putString(key, value)
        editor.apply()
    }



}