package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.R
import com.google.android.material.appbar.MaterialToolbar

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
}