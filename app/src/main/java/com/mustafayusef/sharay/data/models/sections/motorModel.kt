package com.mustafayusef.sharay.data.models.sections

data class motorModel(
    val `data`: List<motorData>,
    val errMsg: String,
    val user: motorUserD
)
data class motorData(
    val MotorImages: List<Any>,
    val active: Boolean,
    val color: String,
    val description: String,
    val id: Int,
    val image: String,
    val location: String,
    val miles: String,
    val name: String,
    val phone: String,
    val price: Int,
    val sell_name: String,
    val state: String,
    val status: String,
    val title: String,
    val type: String,
    val year: String
)
data class motorUserD(
    val id: Int,
    val name: String
)