package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase

import android.app.Application
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.R
import com.google.firebase.auth.FirebaseAuth

class MyFirebaseAuth(val appContext: Application) {

    private val firebaseAuth = FirebaseAuth.getInstance()

    //Opcional perquè pot ser que no hi hagi currentUser
    fun getUsername() = firebaseAuth.currentUser?.displayName ?: appContext.getString(R.string.unknown_user)

}