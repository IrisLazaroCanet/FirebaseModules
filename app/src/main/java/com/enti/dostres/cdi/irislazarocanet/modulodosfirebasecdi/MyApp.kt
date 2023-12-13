package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi

import android.app.Activity
import android.app.Application
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase.FB

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

    fun CloseKeyboard(focusedView: View)
    {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(focusedView.windowToken, 0)
    }
}