package com.bancosantander.puestos.util

import android.app.Activity
import com.bancosantander.puestos.application.App
import com.mibaldi.viewmodelexamplemvp.base.BaseMvpActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.*


val Activity.app: App get() = application as App

fun Date.firebase():String{
    return android.text.format.DateFormat.format("ddMMyy", this).toString()
}

fun Date.presentation():String{
    return android.text.format.DateFormat.format("dd-MM-yyyy ", this).toString()
}

fun consume(f: ()->Unit) : Boolean{
    f()
    return true
}