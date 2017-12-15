package com.bancosantander.puestos.data.models

data class Reservation(
	var id: String,
	var idWorkstation: String,
	var idUser: String,
	var type: Type = Type.Free,
	var iniDate: String = "2017-11-29",
	var endDate: String = "2017-11-29")
{
	enum class Type(name: String) { Free("Free"), Occupied("Occupied"), Unavailable("Unavailable") }
}
