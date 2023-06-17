package com.example.myresto.restosapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RestoAppContext: Application() {
//    init{
//        app = this
//    }
//
//    companion object{
//        private lateinit var app: RestoAppContext
//        fun getAppContext() : Context = app.applicationContext
//    }
}