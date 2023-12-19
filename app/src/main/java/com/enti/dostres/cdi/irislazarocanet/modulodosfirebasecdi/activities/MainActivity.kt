package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.R
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase.FB
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity(){

    private val requestNotificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_screen)

        FB.crashlytics.logSimpleError("MainActivity error") {
            key("Error", "Kernel Panic")
            key("Cuanto azucar quieres", true)
        }

        setTheme(FB.remoteConfig.getTheme().themeId)

        askNotificationPermission()

    }

    fun onNotificationPermissionResponse(isGranted: Boolean) {
        if(isGranted) {
            FirebaseMessaging.getInstance().token
                .addOnSuccessListener { token ->
                    Log.d("Token", "Token::>" + token)
                }
                .addOnFailureListener {
                    //TODO ERROR -> Toast or crashalytics
                }

        } else {
            //TODO ERROR -> Toast or crashalytics
        }
    }

    fun askNotificationPermission() {
        val permision = Manifest.permission.POST_NOTIFICATIONS

        if(ContextCompat.checkSelfPermission(this, permision) == PackageManager.PERMISSION_GRANTED) {

            //Com que ja ha donat el permís, ja podem agafar el token de firebase
            FirebaseMessaging.getInstance().token
                .addOnSuccessListener { token ->
                    Log.d("Token", "Token::>" + token)
                }
                .addOnFailureListener {
                    //TODO ERROR -> Toast or crashalytics
                }

        } else if (shouldShowRequestPermissionRationale(permision)) {
            //Move to Setup Screen, can show popup
            //Ja li havia demanat el permís, m'ha dit que no

        } else {
            //Can open request pop up
            requestNotificationPermissionLauncher.launch(permision)

        }
    }

}