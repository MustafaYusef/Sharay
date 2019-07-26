package com.mustafayusef.sharay.data.models

data class storesModel(
    val data: List<DataStores>,
    val errMsg: String
)
data class DataStores(
    val image: String,
    val location: String,
    val phone: String
)