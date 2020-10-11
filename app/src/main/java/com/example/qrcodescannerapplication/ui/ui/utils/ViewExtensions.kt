package com.example.qrcodescannerapplication.ui.ui.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
fun Calendar.toFormattedDisplay() : String{
    val simpleDateFormat = SimpleDateFormat("dd-mm-yyyy hh:mm a", Locale.US)
    return simpleDateFormat.format(this.time)
}