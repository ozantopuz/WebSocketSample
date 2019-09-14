package com.app.websocketsample.core.extension

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.toast(message: String, duration: Int = Toast.LENGTH_LONG) =
    this.runOnUiThread { Toast.makeText(this, message, duration).show() }
