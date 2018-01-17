package com.bancosantander.puestos.data.models


data class CommonArea(
		val type: Type = Type.Restrooms,
		val positionX: Long = 0,
		val positionY: Long = 0)
{
	fun setPosition(x: Long, y: Long) = CommonArea(type, x, y)
	enum class Type(name: String) {
		Restrooms("Restrooms"),
		Meetingroom("Meetingroom"),
		Reception("Reception"),
		Canteen("Canteen")
	}

}

