package com.mustafayusef.sharay.data.models.sections

data class PartsModel(
    val `data`: List<partsData>,
    val errMsg: String,
    val user: partUser
)
data class partsData(
    val active: Boolean,
    val description: String,
    val detailes: String,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val phone: String,
    val price: Int,
    val state: String

)
data class partUser(
    val id: Int,
    val name: String
)