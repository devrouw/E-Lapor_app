package com.lollipop.e_lapor.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.manager.RequestManagerRetriever
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.util.Preconditions
import com.lollipop.e_lapor.R


class GlideUtil {
    fun with(view: View): RequestManager {
        return getRetriever(view.context)[view]
    }

    private fun getRetriever(context: Context?): RequestManagerRetriever {
        // Context could be null for other reasons (ie the user passes in null), but in practice it will
        // only occur due to errors with the Fragment lifecycle.
        Preconditions.checkNotNull(
            context,
            "You cannot start a load on a not yet attached View or a Fragment where getActivity() "
                    + "returns null (which usually occurs when getActivity() is called before the Fragment "
                    + "is attached or after the Fragment is destroyed)."
        )
        return Glide.get((context)!!).requestManagerRetriever
    }

    companion object GlideBuilder {
        /**
         * function untuk menampilkan gambar menggunakan glide
         *
         * [context] : context
         *
         * [url] : link url image
         *
         * [view] : view yang ingin menampilkan gambar, contoh : _binding.ivThumbnail
         *
         * [requestOption] : placeholder dan resize dari image tersebut, contoh : GlideUtil.FIT_CENTER
         *
         *
         * @author andi 071931
         */
        fun buildDefaultGlide(
            context: Context, url: String,
            view: ImageView,
            requestOptions: RequestOptions,
            placeholder: Int? = R.drawable.ic_profil_picture
        ) {
            try {
                Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .placeholder(placeholder!!)
                    .dontAnimate()
                    .into(view)
            } catch (ex: Exception) {
                Glide.with(context)
                    .load(placeholder)
                    .into(view)
            }
        }

        fun buildGlideWithCorner(
            context: Context, url: String,
            view: ImageView,
            requestOptions: RequestOptions,
            placeholder: Int? = R.drawable.ic_profil_picture,
            corner: Int
        ) {
            try {
                Glide.with(context)
                    .load(url)
                    .apply(requestOptions)
                    .placeholder(placeholder!!)
                    .transform(CenterInside(), RoundedCorners(corner))
                    .dontAnimate()
                    .into(view)
            } catch (ex: Exception) {
                Glide.with(context)
                    .load(placeholder)
                    .into(view)
            }
        }

        fun buildDefaultGlideWithoutPlaceHolder(
            context: Context, url: String,
            view: ImageView
        ) {
            try {
                Glide.with(context)
                    .load(url)
                    .into(view)
            } catch (ex: Exception) {
                Glide.with(context)
                    .load(R.drawable.ic_profil_picture)
                    .into(view)
            }
        }

        val FIT_CENTER = RequestOptions()
            .placeholder(R.drawable.ic_profil_picture)
            .fitCenter()

        val CENTER_CROP = RequestOptions()
            .placeholder(R.drawable.ic_profil_picture)
            .centerCrop()

        val NO_CROP = RequestOptions()
            .placeholder(R.drawable.ic_profil_picture)
    }

}