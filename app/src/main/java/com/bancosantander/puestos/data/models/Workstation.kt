package com.bancosantander.puestos.data.models


data class Workstation(
		val idOwner: String = "",
		var idUser: String = "",
		val name: String = "",
		var status: Status = Status.Unavailable,
		val x: Float = 0f,
		val y: Float = 0f)
{
	enum class Status(name: String) { Free("Free"), Occupied("Occupied"), Unavailable("Unavailable") }

	fun setPosition(x: Float, y: Float) = Workstation(idOwner, idUser, name, status, x, y)
}

