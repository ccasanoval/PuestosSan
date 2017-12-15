package com.bancosantander.puestos.util

import com.bancosantander.puestos.BuildConfig

object Log {
	fun e(tag: String, msg: String, e: Throwable? = null) {
		if(BuildConfig.DEBUG)
			android.util.Log.e(tag, msg, e)
	}
}
