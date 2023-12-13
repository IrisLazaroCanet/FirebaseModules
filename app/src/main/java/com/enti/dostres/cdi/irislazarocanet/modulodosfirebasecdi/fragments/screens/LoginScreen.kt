package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.fragments.screens

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.R
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase.FB
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase.models.DataBaseUser
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.fragments.components.AppDrawer
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.common.SignInButton
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import java.util.Date

class LoginScreen : Fragment() {

    lateinit var fragmentView : View

    /*
    val usernameContainer by lazy { fragmentView.findViewById<MaterialCardView>(R.id.usernameInputContainer) }
    val usernameInput by lazy { fragmentView.findViewById<TextInputLayout>(R.id.usernameInput) }

    val passwordContainer by lazy { fragmentView.findViewById<MaterialCardView>(R.id.passwordInputContainer) }
    val passwordInput by lazy { fragmentView.findViewById<TextInputLayout>(R.id.passwordInput) }

    val verifyPasswordContainer by lazy { fragmentView.findViewById<MaterialCardView>(R.id.verifyPasswordInputContainer) }
    val verifyPasswordInput by lazy { fragmentView.findViewById<TextInputLayout>(R.id.verifyPasswordInput) }

    val emailLoginButton by lazy {fragmentView.findViewById<MaterialButton>(R.id.loginButton)}
    val registerButton by lazy {fragmentView.findViewById<MaterialButton>(R.id.registerButton)}
    */

    val googleAuthButton by lazy {fragmentView.findViewById<SignInButton>(R.id.login_google_button)}

    //Delegate function
    //Interfaz, contrato y protocolo son similares
    val signInLaucher = registerForActivityResult(FirebaseAuthUIActivityResultContract()) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.login_screen, container, false)
        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*emailLoginButton.setOnClickListener {emailLogin()}
        registerButton.setOnClickListener {startRegister()}
        */
        googleAuthButton.setOnClickListener {googleAuth()}

    }

    private fun emailLogin() {

    }

    private fun startRegister() {
        /*verifyPasswordContainer.visibility = View.VISIBLE

        emailLoginButton.text = getString(R.string.back_to_login_button)
        registerButton.text = getString(R.string.end_register_button)

        emailLoginButton.setOnClickListener {
            verifyPasswordContainer.visibility = View.GONE

            emailLoginButton.text = getString(R.string.login_button)
            registerButton.text = getString(R.string.register_button)

            emailLoginButton.setOnClickListener {emailLogin()}
            registerButton.setOnClickListener {startRegister()}

        }

        registerButton.setOnClickListener { endRegister() }*/
    }

    private fun endRegister() {

    }

    private fun googleAuth() {
        val providers = arrayListOf(
            //AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
        )

        //Un intent nos permite abrir otras activities
        //Paso antes de crearla, parap reparar información previa que pueda necesitar
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        signInLaucher.launch(signInIntent)
    }

    //AuthUI nos facilita ya toda la UI
    //Ponemos res como variable porque hace una llamada asíncrona (no sabemos cuándo va a volver)
    //En el momento en que la llame, la función se va a acabar
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
       if(result.resultCode != RESULT_OK) {
           FB.crashlytics.logSimpleError("Login Error") {
               key("code", result.resultCode)
               key("data", result.toString())
           }
           sendToastError()
           return
       }

        val authUser = FB.auth.GetAuthenticationDatabaseUser()?: kotlin.run {
            FB.crashlytics.logSimpleError("Login Error No User") {
                key("code", result.resultCode)
                key("data", result.toString())
            }
            sendToastError()
            return
        }

        val id = authUser.id?: kotlin.run {
            FB.crashlytics.logSimpleError("Login Error No ID") {
                key("code", result.resultCode)
                key("data", result.toString())
            }
            sendToastError()
            return
        }

        FB.dataBase.find<DataBaseUser>(id, authUser.GetTable(),
            onSuccess = { dbUser ->
                dbUser.lastLogin = Date()

                finalSaveUser(dbUser)
            },
            onFailure = {
                finalSaveUser(authUser)
            })
    }

    private fun finalSaveUser(dbUser: DataBaseUser)
    {
        FB.dataBase.save(dbUser,
            onSuccess = { dbUser ->
                FB.auth.SetCurrentUser(dbUser)
                sendToastSuccessAndClose()
            },
            onFailure = {
                sendToastError()
            }
        )
    }

    private fun sendToastError() {
        Snackbar.make(
            AppDrawer.get().fragmentView,
            getString(R.string.login_error),
            Snackbar.LENGTH_LONG)
            .show()
    }

    private fun sendToastSuccessAndClose() {
        Snackbar.make(
            AppDrawer.get().fragmentView,
            getString(R.string.user_login_message, FB.auth.GetUser()?.username),
            Snackbar.LENGTH_LONG)
            .show()
        parentFragmentManager.popBackStack()
    }
}