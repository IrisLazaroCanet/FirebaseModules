package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.fragments.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.R
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase.FB
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.Firebase
import com.google.firebase.crashlytics.crashlytics
import com.google.firebase.crashlytics.setCustomKeys

class AppToolbar : Fragment() {
    companion object {
        //late init perquè garantitzem que l'usarem (no cal optional, no estarà null quan ho anem a buscar)
        private lateinit var Instance: AppToolbar
        fun get() = Instance
    }

    lateinit var toolbar: MaterialToolbar

    //Quan es crea -> com "Awake"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Instance = this
    }

    //Quan el cicle de vida li sol·licita crear la vista (ex: refrescar la vista) -> com "Awake"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.component_toolbar, container, false)
        toolbar = view.findViewById(R.id.AppToolbar)
        return view
    }

    //Com "Start"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.setNavigationOnClickListener {
            //Des d'aquí, obriríem el menú drawer (es posa sempre per sobre)
            AppDrawer.get().openDrawer()
        }

        toolbar.setOnMenuItemClickListener {menuItem ->

            when(menuItem.itemId) {
                R.id.toolbar_button_test -> {
                    //Fem el codi que necessitem
                    //throw RuntimeException("Test crash")
                    val exception = Exception("Test Error")
                    FB.crashlytics.logSimpleError("Some error") {
                        key("Some Name", "Abraham")
                        key("Is failing", true)
                        key("Level 0 of fail", 9001)
                    }
                }
            }

            true
        }
    }
}