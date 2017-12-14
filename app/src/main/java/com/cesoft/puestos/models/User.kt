package com.cesoft.puestos.models

/**
 * Created by ccasanova on 29/11/2017
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
data class User(
	var id: String = "",
	var name: String = "",
	var type: Type = Type.Interim){
	enum class Type(name: String) { Admin("Admin"), Fixed("Fixed"), Interim("Interim") }
}