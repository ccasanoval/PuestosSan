package com.bancosantander.puestos.data.models

/**
 * Created by ccasanova on 29/11/2017
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
data class User(
	val id: String = "",
	val name: String = "",
	val type: Type = Type.Interim){
	enum class Type(name: String) { Admin("Admin"), Fixed("Fixed"), Interim("Interim") }
}