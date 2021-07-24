package com.lollipop.e_lapor.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.lollipop.e_lapor.R
import com.lollipop.e_lapor.databinding.ItemListAduanBinding
import com.lollipop.e_lapor.service.model.AduanResult
import com.lollipop.e_lapor.service.model.PerbaikanResult
import com.lollipop.e_lapor.util.Constant
import com.lollipop.e_lapor.util.GlideUtil

class PengaduanAdapter : RecyclerView.Adapter<PengaduanAdapter.ViewHolder>() {
    private var _items: MutableList<AduanResult> = ArrayList()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setList(list: List<AduanResult>?) {
        if (list == null) return
        this._items.clear()
        this._items.addAll(list)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PengaduanAdapter.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_aduan, parent, false)
    )

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val item = _items[position]
            with(binding) {
                item.foto_aduan?.let {
                    GlideUtil.buildDefaultGlide(itemView.context,
                        "${Constant.URL.IMAGE_URL}${it}",ivImage,GlideUtil.CENTER_CROP,R.drawable.ic_baseline_image_not_supported)
                }
                ivImage.setOnClickListener {
                    onItemClickCallback.onItemClick(item)
                }
            }
        }
    }

    override fun getItemCount() = _items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemListAduanBinding.bind(view)
    }

    interface OnItemClickCallback {
        fun onItemClick(item: AduanResult)
    }
}