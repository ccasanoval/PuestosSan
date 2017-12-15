package com.bancosantander.puestos.data.models


data class Workstation(
	val idOwner: String = "",
	val idUser: String = "",
	val name: String = "",
	val status: Status = Status.Unavailable,
	val x: Float = 0f,
	val y: Float = 0f)
{
	enum class Status(name: String) { Free("Free"), Occupied("Occupied"), Unavailable("Unavailable") }

	fun copy(x: Float, y: Float) = Workstation(idOwner, idUser, name, status, x, y)
}

