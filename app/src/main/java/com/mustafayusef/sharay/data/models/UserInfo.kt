package com.mustafayusef.sharay.data.models

import android.os.Parcelable
import java.io.Serializable

data class UserInfo(
    val Cars: List<UserCars>,
    val active: Boolean,
    val admin: String,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String
):Serializable

data class UserCars(
    val `class`: String,
    val active: Boolean,
    val airBags: String,
    val brand: String,
    val camera: Boolean,
    val color: String,
    val cylinders: Int,
    val date: String,
    val description: String,
    val doors: String,
    val driveSystem: String,
    val fuel: String,
    val gear: String,
    val horse: Int,
    val id: Int,
    val image: String,
    val isUsed: Boolean,
    val lamps: String,
    val location: String,
    val mileage: Int,
    val model: String,
    val name: String,
    val phone: String,
    val price: Int,
    val roof: String,
    val seats: String,
    val sensors: String,
    val status: String,
    val storeId: Int,
    val title: String,
    val turbo: Boolean,
    val type: String,
    val userId: Int,
    val warid: String,
    val wheelSize: Int,
    val window: String,
    val year: String
):Serializable