package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase

import android.app.Application
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.R
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.clases.firebase.models.DataBaseUser
import com.google.firebase.auth.FirebaseAuth

class MyFirebaseAuth(val appContext: Application) {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private var currentUser: DataBaseUser? = null

    fun IsLoginActive() = GetUser() != null

    fun SetCurrentUser(newUser: DataBaseUser) {
        currentUser = newUser
    }

    fun GetUser() = currentUser

    fun GetAuthenticationDatabaseUser(): DataBaseUser? {
        firebaseAuth.currentUser?.let { user ->
            val dataBaseUser = DataBaseUser(
                id = user.uid,
                email = user.email,
                username = user.displayName,
                photoPath = user.photoUrl.toString()
            )
            return dataBaseUser
        }
        return null
    }

    //Opcional perquè pot ser que no hi hagi currentUser
    //fun getUsername() = firebaseAuth.currentUser?.displayName ?: appContext.getString(R.string.unknown_user)

}