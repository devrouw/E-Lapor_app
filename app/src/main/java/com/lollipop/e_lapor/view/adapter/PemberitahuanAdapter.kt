package com.lollipop.e_lapor.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.lollipop.e_lapor.R
import com.lollipop.e_lapor.databinding.ItemListNotifikasiBinding
import com.lollipop.e_lapor.service.model.PerbaikanResult
import com.lollipop.e_lapor.util.Constant
import com.lollipop.e_lapor.util.GlideUtil

class PemberitahuanAdapter : RecyclerView.Adapter<PemberitahuanAdapter.ViewHolder>() {
    private var _items: MutableList<PerbaikanResult> = ArrayList()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setList(list: List<PerbaikanResult>?) {
        if (list == null) return
        this._items.clear()
        this._items.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PemberitahuanAdapter.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_notifikasi, parent, false)
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val item = _items[position]
            with(binding) {
                item.foto_perbaikan?.let {
                    GlideUtil.buildDefaultGlide(itemView.context,
                        "${Constant.URL.IMAGE_URL}${it}",ivProfil,GlideUtil.CENTER_CROP,R.drawable.ic_baseline_image_not_supported)
                }
                tvJudul.text = item.dinas
                tvKeterangan.text = item.keterangan
                tvKategori.text = item.kategori
                clContainer.setOnClickListener {
                    onItemClickCallback.onItemClick(item)
                }
            }
        }
    }

    override fun getItemCount() = _items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemListNotifikasiBinding.bind(view)
    }

    interface OnItemClickCallback {
        fun onItemClick(item: PerbaikanResult)
    }
}