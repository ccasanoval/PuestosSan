package com.bancosantander.puestos.data.models


data class User(
	val id: String = "",
	val name: String = "",
	val fullname: String = "",
	val email: String = "",
	val channel: String = "",
	val hadChangedPass: Boolean = false,
	val type: Type = Type.Interim){
	enum class Type(name: String) { Admin("Admin"), Fixed("Fixed"), Interim("Interim") }
	enum class IdType(name: String){ idOwner("idOwner"), idUser("idUser")}

	override fun toString(): String {
		return fullname
	}
}