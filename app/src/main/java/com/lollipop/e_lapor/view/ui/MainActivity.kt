package com.lollipop.e_lapor.view.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lollipop.e_lapor.R
import com.lollipop.e_lapor.databinding.ActivityMainBinding
import com.lollipop.e_lapor.view.ui.fragment.ProfileFragment
import com.lollipop.e_lapor.viewModel.DataStoreViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var _binding: ActivityMainBinding

    private lateinit var _viewModelDataStore: DataStoreViewModel
    private val _profilFragment = ProfileFragment()

    var isDetailOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        initializeViewModel()

        with(_binding){
            ivMenu.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }

        observeDataStore()
    }

    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
    }

    private fun observeDataStore() {
        _viewModelDataStore.loginStatus.observe(this, {
            if(!it){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }else{
                replaceFragment(_profilFragment)
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fr_container, fragment)
        transaction.commit()
    }

    fun addFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        val tag = if (isDetailOpen) "tag" else "bottom"

        transaction.add(R.id.fr_container, fragment, tag)

        transaction.addToBackStack(tag)
        transaction.commit()
    }

}