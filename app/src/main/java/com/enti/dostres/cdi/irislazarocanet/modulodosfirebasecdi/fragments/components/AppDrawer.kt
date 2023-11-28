package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.fragments.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.R
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.fragments.screens.LoginScreen
import com.google.android.material.navigation.NavigationView

class AppDrawer : Fragment() {

    companion object {
        private lateinit var Instance:AppDrawer
        fun get() = Instance
    }

    lateinit var fragmentView: View
    private val drawer by lazy {fragmentView.findViewById<DrawerLayout>(R.id.AppDrawer)}
    private val navigationDrawer by lazy {fragmentView.findViewById<NavigationView>(R.id.navigationDrawer)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Instance = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentView = inflater.inflate(R.layout.component_navigationdrawer, container, false)

        return fragmentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigationDrawer.setNavigationItemSelectedListener { menuItem ->

            when(menuItem.itemId)
            {
                R.id.login_drawer_button -> {

                    val loginScreen = LoginScreen()
                    val transaction = childFragmentManager.beginTransaction()
                    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right,
                        R.anim.enter_from_right, R.anim.exit_to_right)

                    //Si hi havia una altra cosa dins el reusable, la substituirà per loginScreen
                    transaction.replace(R.id.reusableDialogsContainer, loginScreen)
                        .addToBackStack(null)
                        .commit()

                    drawer.close()
                }
            }

            true
        }
    }

    fun openDrawer()
    {
        drawer.open()
    }
}