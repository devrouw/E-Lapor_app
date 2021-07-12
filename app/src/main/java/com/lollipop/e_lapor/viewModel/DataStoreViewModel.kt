package com.lollipop.e_lapor.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.lollipop.e_lapor.view.ApplicationController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class DataStoreViewModel(application: Application) : AndroidViewModel(application) {
    private val dataStore = (application as ApplicationController).dataStoreRepository

    val loginStatus = dataStore.readLoginStatus.asLiveData()
    val userData = dataStore.readUserData.asLiveData()

    /**
     * Store token, refresh token, email in data store
     */
    fun setLoginStatus(value: Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveLoginState(value)
        }
    }

    /**
     * Store email in datastore
     */
    fun setAuthPref(nik: String, namaLengkap: String, noTelp: String, fotoProfil: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveAuth(nik, namaLengkap, noTelp, fotoProfil)
        }
    }

//    fun getNik(): Flow<String> {
//        return dataStore.readNik
//    }
}