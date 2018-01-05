package com.bancosantander.puestos.util

import android.app.Activity
import com.bancosantander.puestos.application.App
import java.util.*


val Activity.app: App get() = application as App

fun Date.firebase():String{
    return android.text.format.DateFormat.format("ddMMyy ", this).toString()
}