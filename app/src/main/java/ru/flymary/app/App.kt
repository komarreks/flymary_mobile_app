package ru.flymary.app

import android.app.Application
import com.bumptech.glide.Glide

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        FIRST_START = true
    }

    companion object{

        var FIRST_START = false

    }
}