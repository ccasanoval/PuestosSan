package com.bancosantander.puestos.util

import android.graphics.PointF

class Astar {
	fun calcMapa(ini: PointF, end: PointF, mapa: ByteArray, cols: Int, rows: Int)
			= calcMapa(ini.x.toInt(), ini.y.toInt(), end.x.toInt(), end.y.toInt(), mapa, cols, rows)

	external fun calcMapa(
		iniX: Int, iniY: Int,
		endX: Int, endY: Int,
		mapa: ByteArray,
		cols: Int, rows: Int): String
	companion object {
		init {
			System.loadLibrary("native-lib")
		}
	}
}