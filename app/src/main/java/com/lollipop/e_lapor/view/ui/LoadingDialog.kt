package com.lollipop.e_lapor.view.ui

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.WindowManager
import com.lollipop.e_lapor.R

class LoadingDialog(private val activity: Activity) {
    private lateinit var _dialog: AlertDialog

    fun startLoadingDialog() {
        val builder = AlertDialog.Builder(activity, R.style.my_dialog)
        val inflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.circle_loading_dialog, null))
        builder.setCancelable(false)
        _dialog = builder.create()
        _dialog.show()

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(_dialog.window?.attributes)
        lp.width = 100
        lp.height = 100
        _dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        _dialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        _dialog.window?.setGravity(Gravity.CENTER)
        _dialog.window?.attributes = lp
    }

    fun dismiss() {
        if (::_dialog.isInitialized) {
            _dialog.dismiss()
        }
    }
}