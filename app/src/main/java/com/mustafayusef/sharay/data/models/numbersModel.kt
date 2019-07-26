package com.mustafayusef.sharay.data.models

data class numbersModel(
    val data: List<DataNumbers>,
    val errMsg: String
)
data class DataNumbers(
    val image: String,
    val location: String,
    val phone: String,
    val price: Int
)