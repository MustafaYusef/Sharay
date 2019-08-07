package com.mustafayusef.sharay.data.models

import java.io.Serializable

data class CarsModel(
    val `data`: List<DataCars>,
    val errMsg: String,
    val user:carUser
):Serializable

data class DataCars(
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
    val isRent: String,
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
):Serializable
data class carUser(
    val id:Int,
    val name:String
):Serializable
