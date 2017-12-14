package com.cesoft.puestos.models

/**
 * Created by ccasanova on 29/11/2017
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
data class Workstation(
	var idOwner: String = "",
	var idUser: String = "",
	var name: String = "",
	var status: Status = Status.Unavailable,
	var x: Float = 0f,
	var y: Float = 0f){
	enum class Status(name: String) { Free("Free"), Occupied("Occupied"), Unavailable("Unavailable") }

}

