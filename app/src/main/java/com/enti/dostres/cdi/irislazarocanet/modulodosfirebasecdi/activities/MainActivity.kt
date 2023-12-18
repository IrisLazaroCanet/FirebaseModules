package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.R
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase.FB

class MainActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_screen)

        FB.crashlytics.logSimpleError("MainActivity error") {
            key("Error", "Kernel Panic")
            key("Cuanto azucar quieres", true)
        }

        setTheme(FB.remoteConfig.getTheme().themeId)

    }
}