package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase

import android.app.Application

typealias FB = MyFirebase

class MyFirebase {
    companion object {

        lateinit var analytics: MyFirebaseAnalytics
        fun init(appContext: Application)
        {
            analytics = MyFirebaseAnalytics(appContext)
        }
    }
}