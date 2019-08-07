package com.mustafayusef.sharay.data.models

data class banners(
    val `data`: List<bannersData>,
    val errMsg: String,
    val user: bannersUser
)
data class bannersData(
    val active: Boolean,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val phone: String,
    val state: String,
    val storeId: Int?=null
)
data class bannersUser(
    val id: Int,
    val name: String
)