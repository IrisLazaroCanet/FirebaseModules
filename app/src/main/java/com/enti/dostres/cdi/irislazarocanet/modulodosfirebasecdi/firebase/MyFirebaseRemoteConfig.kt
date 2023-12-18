package com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.firebase

import com.enti.dostres.cdi.irislazarocanet.modulodosfirebasecdi.R
import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig


class MyFirebaseRemoteConfig {
    private val remoteConfig = Firebase.remoteConfig

    enum class RemoteKeys(val key: String) {
        CurrentTheme("current_theme")
    }

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 30
        }

        remoteConfig.setConfigSettingsAsync(configSettings)

        //Add defaults
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

        remoteConfig.fetchAndActivate()
    }

    enum class AppTheme(val key: String, val themeId: Int) {
        Base("Base", R.style.Theme_ModuloDosFirebaseCDI),
        Christmas("Christmas", R.style.Theme_ModuloDosFirebaseCDI_Christmas);

        //Constructor estàtic per l'enum
        companion object {
            fun fromKey(key: String) : AppTheme {
                return values().find {theme -> theme.key == key} ?: Base
            }
        }
    }

    fun getTheme(): AppTheme {
        val key = remoteConfig.getString(RemoteKeys.CurrentTheme.key)
        return AppTheme.fromKey(key)
    }
}