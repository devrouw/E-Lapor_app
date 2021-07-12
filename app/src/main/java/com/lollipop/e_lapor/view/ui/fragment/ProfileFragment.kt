package com.lollipop.e_lapor.view.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lollipop.e_lapor.databinding.FragmentProfilBinding
import com.lollipop.e_lapor.view.ui.DetailAduanActivity
import com.lollipop.e_lapor.view.ui.LoginActivity
import com.lollipop.e_lapor.view.ui.MainActivity
import com.lollipop.e_lapor.viewModel.DataStoreViewModel
import com.lollipop.e_lapor.viewModel.MainViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class ProfileFragment : Fragment() {
    private var _bindingFragment: FragmentProfilBinding? = null
    private val _binding get() = _bindingFragment!!

    private lateinit var _viewModelDataStore: DataStoreViewModel
    private lateinit var _viewModel: MainViewModel
    private lateinit var _mContext: Context

    @RequiresApi(Build.VERSION_CODES.M)
    private val _detailAduanFragment = DetailAduanFragment()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingFragment = FragmentProfilBinding.inflate(inflater,container,false)
        initializeViewModel()
        return _binding.root
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(_binding){
            btAdd.setOnClickListener {
                startActivity(Intent(requireActivity(),DetailAduanActivity::class.java))
//                (activity as MainActivity).addFragment(_detailAduanFragment)
//                (activity as MainActivity).isDetailOpen = true
            }
        }

        observableLiveData()
    }

    private fun observableLiveData() {
        _viewModelDataStore.userData.observe(viewLifecycleOwner, {
            _binding.tvNamaLengkap.text = it[1]
            _binding.tvNoTelp.text = it[2]
        })
    }

    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
        _viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }


}