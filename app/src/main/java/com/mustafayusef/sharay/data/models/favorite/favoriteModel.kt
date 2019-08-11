package com.mustafayusef.sharay.data.models.favorite



data class favoriteModel(
    val Car: CarFavoorite?=null,
    val Motor: Any,
    val Number: Any,
    val Part: Any,
    val Store: Any,
    val carId: Int,
    val id: Int,
    val motorId: Int,
    val numberId: Int,
    val partId: Int,
    val storeId: Int,
    val userId: Int
)
data class CarFavoorite(
    val `class`: String,
    val active: Boolean,
    val airBags: String,
    val brand: String,
    val color: String,
    val createdAt: String,
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
    val updatedAt: String,
    val userId: Int,
    val warid: String,
    val window: String,
    val year: String
)