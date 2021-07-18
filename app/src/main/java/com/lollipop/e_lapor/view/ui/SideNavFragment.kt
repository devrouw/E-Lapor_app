package com.lollipop.e_lapor.view.ui

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lollipop.e_lapor.databinding.DialogConfirmationBinding
import com.lollipop.e_lapor.databinding.FragmentSideNavBinding
import com.lollipop.e_lapor.view.ui.fragment.EditDaftarFragment
import com.lollipop.e_lapor.view.ui.fragment.PemberitahuanFragment
import com.lollipop.e_lapor.view.ui.fragment.ProfileFragment
import com.lollipop.e_lapor.viewModel.DataStoreViewModel
import com.lollipop.e_lapor.viewModel.MainViewModel

class SideNavFragment : Fragment() {
    private var _binding: FragmentSideNavBinding? = null
    private val binding get() = _binding!!

    private lateinit var _dialogBinding: DialogConfirmationBinding
    private lateinit var _viewModelDataStore: DataStoreViewModel
    private val _editDaftarFragment = EditDaftarFragment()
    private val _profilFragment = ProfileFragment()
    private val _notifikasiFragment = PemberitahuanFragment()

    private lateinit var _dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSideNavBinding.inflate(inflater, container, false)
        initializeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialogBinding()
    }

    private fun dialogBinding() {
        with(_dialogBinding) {
            btIya.setOnClickListener {
                _viewModelDataStore.setLoginStatus(false)
            }
            btTidak.setOnClickListener {
                _dialog.dismiss()
            }
        }
    }

    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
        _dialogBinding = DialogConfirmationBinding.inflate(layoutInflater)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(binding){
            _dialog = Dialog(requireActivity())
            _dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            _dialog.setContentView(_dialogBinding.root)
            _dialog.window?.setLayout((resources.displayMetrics.widthPixels), ViewGroup.LayoutParams.WRAP_CONTENT)

            tvProfil.setOnClickListener {
                (activity as MainActivity).replaceFragment(_profilFragment)
                (activity as MainActivity).closeDrawer()
            }

            tvPemberitahuan.setOnClickListener {
                (activity as MainActivity).replaceFragment(_notifikasiFragment)
                (activity as MainActivity).closeDrawer()
            }

            tvBiodata.setOnClickListener {
                (activity as MainActivity).replaceFragment(_editDaftarFragment)
                (activity as MainActivity).closeDrawer()
            }

            tvLogout.setOnClickListener {
                _dialog.show()
            }
        }
    }

}