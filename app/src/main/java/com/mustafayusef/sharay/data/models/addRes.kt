package com.mustafayusef.sharay.data.models

data class addRes(
    val `data`: AddData,
    val errMsg: String
)
data class AddData(
    val `class`: String,
    val active: Boolean,
    val airBags: String,
    val brand: String,
    val color: String,
    val createdAt: String,
    val cylinders: String,
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
    val mileage: String,
    val name: String,
    val phone: String,
    val price: String,
    val roof: String,
    val seats: String,
    val state: String,
    val status: String,
    val storeId: String,
    val title: String,
    val type: String,
    val updatedAt: String,
    val userId: Int,
    val warid: String,
    val window: String,
    val year: String
)