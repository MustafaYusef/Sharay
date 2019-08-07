package com.mustafayusef.sharay.data.networks.repostorys

import com.mustafayusef.holidaymaster.networks.SafeApiRequest
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.sections.*
import com.mustafayusef.sharay.data.networks.myApi

class SectionRepostary(val api: myApi): SafeApiRequest() {
    suspend fun getCars():CarsModel{
        return SafeRequest{
            api.GetCars()
        }}

    suspend fun GetRent(): RentModel {
        return SafeRequest {
            api.GetRent()
        }
    }
    suspend fun GetNumbers(): NumbersModel {
        return SafeRequest {
            api.getNumbers()
        }
    }

    suspend fun GetParts(): PartsModel {
        return SafeRequest {
            api.GetPart()
        }
    }

    suspend fun GetStores(): StoresModel {
        return SafeRequest {
            api.getStores()
        }
    }
    suspend fun GetImport(): RentModel {
        return SafeRequest {
            api.GetImport()
        }
    }

    suspend fun GetMotors(): motorModel {
        return SafeRequest {
            api.GetMotor()
        }
    }
    suspend fun GetMotorDetails(id:Int): motorDetailsModel {
        return SafeRequest {
            api.GetMotorDetails(id)
        }
    }


    suspend fun GetStoreDetails(id:Int): StoreDetails {
        return SafeRequest {
            api.GetDetailsStore(id)
        }
    }

}