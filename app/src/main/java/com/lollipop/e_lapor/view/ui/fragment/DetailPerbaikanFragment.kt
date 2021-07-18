package com.lollipop.e_lapor.view.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.lollipop.e_lapor.databinding.FragmentDetailPerbaikanBinding
import com.lollipop.e_lapor.service.model.PerbaikanResult
import com.lollipop.e_lapor.util.Constant
import com.lollipop.e_lapor.util.GlideUtil
import com.lollipop.e_lapor.util.ResultOfNetwork
import com.lollipop.e_lapor.viewModel.DataStoreViewModel
import com.lollipop.e_lapor.viewModel.MainViewModel
import timber.log.Timber

class DetailPerbaikanFragment : Fragment() {
    private var _bindingFragment: FragmentDetailPerbaikanBinding? = null
    private val _binding get() = _bindingFragment!!

    private lateinit var _viewModelDataStore: DataStoreViewModel
    private lateinit var _viewModel: MainViewModel

    private val _id by lazy { arguments?.getString("id_perbaikan").orEmpty() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingFragment = FragmentDetailPerbaikanBinding.inflate(inflater,container,false)
        initializeViewModel()
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(_binding){

        }

        observableLiveData()
    }

    private fun observableLiveData() {
        _viewModelDataStore.userData.observe(viewLifecycleOwner, {
            _viewModel.getDetailPerbaikan("detail_perbaikan",it[0], _id)
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

    private fun isSuccessNetworkCallback(code: Int?, data: List<PerbaikanResult>?) {
        when (code) {
            Constant.Network.REQUEST_NOT_FOUND -> {
                Timber.d("Tidak ada data")
            }
            Constant.Network.REQUEST_SUCCESS -> {
                _binding.tvDinas.setText(data?.get(0)?.dinas)
                _binding.tvKategori.setText("Kategori ${data?.get(0)?.kategori}")
                _binding.tvPesan.setText(data?.get(0)?.pesan)
                _binding.tvBalasan.setText(data?.get(0)?.keterangan)
                GlideUtil.buildDefaultGlide(
                    requireActivity(),"${Constant.URL.IMAGE_URL}${data?.get(0)?.foto_aduan}",_binding.ivAduan,
                    GlideUtil.CENTER_CROP
                )
                GlideUtil.buildDefaultGlide(
                    requireActivity(),"${Constant.URL.IMAGE_URL}${data?.get(0)?.foto_perbaikan}",_binding.ivPerbaikan,
                    GlideUtil.CENTER_CROP
                )
                GlideUtil.buildDefaultGlide(
                    requireActivity(),"${Constant.URL.IMAGE_URL}${data?.get(0)?.foto_profil}",_binding.ivWarga,
                    GlideUtil.CENTER_CROP
                )
            }
        }
    }

    private fun initializeViewModel() {
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
        _viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

}