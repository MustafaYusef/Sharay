package com.mustafayusef.sharay.data.models.sections

data class StoreDetails(
    val `data`: Data,
    val errMsg: String,
    val user: User
)
data class Car(
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
    val isImported: Any,
    val isRent: Any,
    val location: String,
    val mileage: Int,
    val name: String,
    val phone: String,
    val price: Int,
    val roof: String,
    val seats: String,
    val state: String,
    val status: String,
    val storeId: Int,
    val title: String,
    val type: String,
    val userId: Int,
    val warid: String,
    val window: String,
    val year: String
)
data class Data(
    var Cars: List<Car>?=null,
    val active: Boolean,
    val id: Int,
    val image: String,
    val location: String,
    val name: String,
    val phone: String,
    val state: String
)
data class User(
    val id: Int,
    val name: String
)