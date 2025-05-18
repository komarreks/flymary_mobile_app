package ru.flymary.app

import android.annotation.SuppressLint
import android.app.Application
import com.bumptech.glide.Glide
import ru.flymary.app.localstorage.LocalStorage
import ru.flymary.app.model.basket.Basket

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        FIRST_START = true
        LOCAL_STORAGE = LocalStorage(this)
    }

    companion object{

        var FIRST_START = false
        @SuppressLint("StaticFieldLeak")
        lateinit var LOCAL_STORAGE:LocalStorage
        lateinit var basket: Basket
    }
}