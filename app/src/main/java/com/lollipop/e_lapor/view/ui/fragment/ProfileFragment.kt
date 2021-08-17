package com.lollipop.e_lapor.view.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.lollipop.e_lapor.databinding.FragmentProfilBinding
import com.lollipop.e_lapor.service.model.AduanResult
import com.lollipop.e_lapor.util.Constant
import com.lollipop.e_lapor.util.GlideUtil
import com.lollipop.e_lapor.util.ResultOfNetwork
import com.lollipop.e_lapor.view.adapter.PengaduanAdapter
import com.lollipop.e_lapor.view.ui.DetailAduanActivity
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
    private lateinit var _adapter: PengaduanAdapter

    private var _nik = ""

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        _mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _bindingFragment = FragmentProfilBinding.inflate(inflater,container,false)
        initializeViewModel()
        return _binding.root
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _initializeAdapter()

        with(_binding){
            btAdd.setOnClickListener {
                startActivity(Intent(requireActivity(),DetailAduanActivity::class.java))
            }
        }

        observableLiveData()
    }

    private fun _initializeAdapter() {
        _binding.rvContent.layoutManager = GridLayoutManager(requireActivity(), 3)
        _adapter = PengaduanAdapter()
        _adapter.setOnItemClickCallback(object : PengaduanAdapter.OnItemClickCallback{
            override fun onItemClick(item: AduanResult) {
                val fragment = DetailPerbaikanFragment()
                val bundle = Bundle().apply {
//                    putString("id_perbaikan", item.id_perbaikan_fk)
                    putString("id_pengaduan", item.id_pengaduan)
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
            _viewModel.getListAduan("list_aduan",it[0])
            _binding.tvNamaLengkap.text = it[1]
            _binding.tvNoTelp.text = it[2]
            GlideUtil.buildDefaultGlide(
                requireActivity(),"${Constant.URL.IMAGE_URL}${it[3]}",_binding.cvProfil,GlideUtil.CENTER_CROP
            )
        })

        _viewModel.aduanData.observe(viewLifecycleOwner, {
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

    private fun isSuccessNetworkCallback(code: Int?, data: List<AduanResult>?) {
        when (code) {
            Constant.Network.REQUEST_NOT_FOUND -> {
                _binding.ivNoImage.visibility = View.VISIBLE
            }
            Constant.Network.REQUEST_SUCCESS -> {
                data.let {
                    _adapter.setList(data)
                }
            }
        }
    }


}