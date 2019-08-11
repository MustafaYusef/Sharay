package com.mustafayusef.sharay.data.models

data class carDetails(
    var `data`: List<DataCarDetails>?=null,
    val errMsg: String
)
 data class imageData(
     val id: Int,
     val image: String,
     val carId: Int
 )

data class DataCarDetails(
    val CarImages: List<imageData?>,
    val Store: store?,
    val `class`: String,
    val active: Boolean,
    val airBags: String,
    val brand: String,
    val color: String,
    val cylinders: Int,
    val date: String,
    val description: String,
    val driveSystem: String,
    val fuel: String,
    val gear: String,
    val id: Int,
    val image: String,
    val isRent: Boolean,
    val isImported:Boolean,
    val location: String,
    val mileage: Int,
    val name: String,
    val phone: String,
    val price: Int,
    val roof: String,
    val seats: String,
    val status: String,
    val storeId: Int,
    val title: String,
    val type: String,
    val userId: Int,
    val warid: String,
    val window: String,
    val year: String
)
data class store(
    val active: Boolean,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val phone: String
)