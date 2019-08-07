package com.mustafayusef.sharay.data.models.sections

data class NumbersModel(
    val `data`: List<NumberData>,
    val errMsg: String,
    val user: NumberUser
)
data class NumberUser(
    val id: Int,
    val name: String
)

data class NumberData(
    val active: Boolean,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val phone: String,
    val price: Int
)