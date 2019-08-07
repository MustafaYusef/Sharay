package com.mustafayusef.sharay.data.models.sections

data class motorDetailsModel(
    val `data`: motorDataDetails,
    val errMsg: String,
    var user: MotorUser
)
data class motorDataDetails(
    var MotorImages: List<MotorImage>,
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
data class MotorUser(
    val id: Int,
    val name: String
)
data class MotorImage(
    val active: Boolean,
    val id: Int,
    val image: String,
    val motorId: Int
)