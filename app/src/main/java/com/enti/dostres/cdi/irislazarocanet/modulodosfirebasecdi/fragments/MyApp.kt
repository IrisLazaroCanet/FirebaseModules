package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.fragments

import android.app.Application
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase.FB
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase.MyFirebase

class MyApp : Application() {

    companion object {
        private lateinit var instance : MyApp

        fun get() = instance
    }

    override fun onCreate()
    {
        super.onCreate()
        instance = this
        FB.init(this)
        FB.analytics.logOpenApp()
    }
}