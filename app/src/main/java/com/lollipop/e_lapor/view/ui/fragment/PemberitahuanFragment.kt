package com.lollipop.e_lapor.view.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.lollipop.e_lapor.databinding.FragmentPemberitahuanBinding
import com.lollipop.e_lapor.service.model.PerbaikanResult
import com.lollipop.e_lapor.util.Constant
import com.lollipop.e_lapor.util.ResultOfNetwork
import com.lollipop.e_lapor.view.adapter.PemberitahuanAdapter
import com.lollipop.e_lapor.view.ui.MainActivity
import com.lollipop.e_lapor.viewModel.DataStoreViewModel
import com.lollipop.e_lapor.viewModel.MainViewModel
import timber.log.Timber

class PemberitahuanFragment : Fragment() {
    private var _bindingFragment: FragmentPemberitahuanBinding? = null
    private val _binding get() = _bindingFragment!!

    private lateinit var _viewModelDataStore: DataStoreViewModel
    private lateinit var _viewModel: MainViewModel
    private lateinit var _mContext: Context
    private lateinit var _adapter: PemberitahuanAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingFragment = FragmentPemberitahuanBinding.inflate(inflater,container,false)
        initializeViewModel()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _initializeAdapter()

        with(_binding){

        }
        observableLiveData()
    }

    private fun _initializeAdapter() {
        _binding.rvContent.layoutManager = LinearLayoutManager(requireActivity())
        _adapter = PemberitahuanAdapter()
        _adapter.setOnItemClickCallback(object : PemberitahuanAdapter.OnItemClickCallback{
            override fun onItemClick(item: PerbaikanResult) {
                val fragment = DetailPerbaikanFragment()
                val bundle = Bundle().apply {
//                    putString("id_perbaikan", item.id_perbaikan)
                    putString("id_pengaduan", item.id_aduan)
                }
                fragment.arguments = bundle
                (activity as MainActivity).addFragment(fragment)
                (activity as MainActivity).isDetailOpen = true
            }

        })
        _binding.rvContent.adapter = _adapter
    }

    private fun observableLiveData() {
        _viewModelDataStore.userData.observe(viewLifecycleOwner, {
            _viewModel.getListPerbaikan("list_perbaikan",it[0])
        })

        _viewModel.perbaikanData.observe(viewLifecycleOwner, {
            when (it) {
                is ResultOfNetwork.Success -> {
                    isSuccessNetworkCallback(
                        it.value.code,
                        it.value.data
                    )
                }
                is ResultOfNetwork.Failure -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
        _viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun isSuccessNetworkCallback(code: Int?, data: List<PerbaikanResult>?) {
        when (code) {
            Constant.Network.REQUEST_NOT_FOUND -> {

            }
            Constant.Network.REQUEST_SUCCESS -> {
                data.let {
                    _adapter.setList(data)
                }
            }
        }
    }

}