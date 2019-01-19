package me.navid.scrambilo.extension

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.LayoutRes
import me.navid.scrambilo.R

fun ViewGroup.inflate(@LayoutRes resource: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(resource, this, attachToRoot)

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this, text, duration).apply {
        val layout = LayoutInflater.from(this@toast).inflate(R.layout.layout_toast, null)
        val tv = layout.findViewById<TextView>(android.R.id.message)
        tv.text = text

        setDuration(duration)
        setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 150)
        view = layout
    }.show()
