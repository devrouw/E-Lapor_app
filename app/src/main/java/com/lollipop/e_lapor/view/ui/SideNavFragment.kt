package com.lollipop.e_lapor.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lollipop.e_lapor.databinding.FragmentSideNavBinding

class SideNavFragment : Fragment() {
    private var _binding: FragmentSideNavBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val ITEM_FAVORITE = 0
        const val ITEM_LOGIN = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSideNavBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


}