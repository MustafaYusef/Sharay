package com.mustafayusef.sharay.data.models.sections

data class StoresModel(
    val `data`: List<StoresData>,
    val errMsg: String,
    val user: StoresUser
)
data class StoresData(
    val active: Boolean,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val phone: String
)
data class StoresUser(
    val id: Int,
    val name: String
)