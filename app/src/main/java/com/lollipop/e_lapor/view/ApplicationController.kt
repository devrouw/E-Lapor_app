package com.lollipop.e_lapor.view

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import com.lollipop.e_lapor.BuildConfig
import com.lollipop.e_lapor.repository.PREFERENCE_NAME
import timber.log.Timber
import timber.log.Timber.DebugTree
import com.lollipop.e_lapor.repository.DataStoreRepository as DataStoreRepository1


class ApplicationController : Application() {

    companion object {
        lateinit var appContext: Context
    }

    lateinit var dataStoreRepository: DataStoreRepository1

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        initDataStore()
    }

    private fun initDataStore() {
        val dataStore: DataStore<Preferences> = appContext.createDataStore(
            name = PREFERENCE_NAME
        )

        dataStoreRepository = DataStoreRepository1(dataStore)
    }

}