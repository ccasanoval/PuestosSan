package com.bancosantander.puestos.data.models


data class CommonArea(
		val type: String = "",
		val positionX: Float = 0f,
		val positionY: Float = 0f)
{
	fun setPosition(x: Float, y: Float) = CommonArea(type, x, y)
}

