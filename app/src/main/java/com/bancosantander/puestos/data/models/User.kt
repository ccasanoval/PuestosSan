package com.bancosantander.puestos.data.models


data class User(
	val id: String = "",
	val name: String = "",
	val type: Type = Type.Interim){
	enum class Type(name: String) { Admin("Admin"), Fixed("Fixed"), Interim("Interim") }
	enum class IdType(name: String){Owner("idOwner"),User("idUser")}
}