package com.mustafayusef.sharay.data.networks.repostorys

import com.mustafayusef.holidaymaster.networks.SafeApiRequest
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.carDetails
import com.mustafayusef.sharay.data.networks.myApi

class SectionRepostary(val api: myApi): SafeApiRequest() {
    suspend fun getCars():CarsModel{
        return SafeRequest{
            api.GetCars()
        }}

    suspend fun GetRent(): CarsModel {
        return SafeRequest {
            api.GetCars()
        }
    }
    suspend fun GetNumbers(): CarsModel {
        return SafeRequest {
            api.GetCars()
        }
    }

    suspend fun GetParts(): CarsModel {
        return SafeRequest {
            api.GetCars()
        }
    }

    suspend fun GetStores(): CarsModel {
        return SafeRequest {
            api.GetCars()
        }
    }
    suspend fun GetWared(): CarsModel {
        return SafeRequest {
            api.GetCars()
        }
    }

    suspend fun GetMotors(): CarsModel {
        return SafeRequest {
            api.GetCars()
        }
    }
}