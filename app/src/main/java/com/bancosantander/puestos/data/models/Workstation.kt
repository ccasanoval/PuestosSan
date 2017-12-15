package com.bancosantander.puestos.data.models


/**
 * Created by ccasanova on 29/11/2017
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
data class Workstation(
	//val id: String = "",
	val idOwner: String = "",
	val idUser: String = "",
	val name: String = "",
	val status: Status = Status.Unavailable,
	val x: Float = 0f,
	val y: Float = 0f)// : Comparable<Workstation>
{
	enum class Status(name: String) { Free("Free"), Occupied("Occupied"), Unavailable("Unavailable") }

	fun copy(x: Float, y: Float) = Workstation(idOwner, idUser, name, status, x, y)

	//override fun compareTo(other: Workstation) = 1000*(abs(x - other.x) + abs(y - other.y)).toInt()
}

