package com.bancosantander.puestos.util

import android.app.Activity
import com.bancosantander.puestos.application.App


val Activity.app: App get() = application as App