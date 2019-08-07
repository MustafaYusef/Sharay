package com.mustafayusef.sharay.data.models.sections

data class RentModel(
    var `data`: List<CarRent>?=null,
    val errMsg: String,
    val user: UserRent
)
data class CarRent(
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
    val isImported: Boolean,
    val isRent: Boolean,
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
data class UserRent(
    val Cars: List<CarRent>,
    val Favorites: List<Any>,
    val active: Boolean,
    val admin: Any,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val phoneSecond: Any,
    val state: Any
)