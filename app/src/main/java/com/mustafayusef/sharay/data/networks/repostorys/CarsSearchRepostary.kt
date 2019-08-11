package com.mustafayusef.sharay.data.networks.repostorys

import com.mustafayusef.holidaymaster.networks.SafeApiRequest
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.data.models.carDetails
import com.mustafayusef.sharay.data.networks.myApi
import com.mustafayusef.sharay.database.databaseApp
import com.mustafayusef.sharay.database.entitis.latestCar

class CarsSearchRepostary(val api:myApi,val db:databaseApp ):SafeApiRequest(){
    suspend fun getCars():CarsModel{
        return SafeRequest{
            api.GetCars()
        }}

    suspend fun getLatest():List<latestCar>{
        return db.Car_Dao().getAllCar()
        }

    suspend fun saveData(car: latestCar):Long{
        return db.Car_Dao().insertCar(car)
    }
    suspend fun deletLast():Int{
        return db.Car_Dao().deleteCars()
    }
    suspend fun getDetailsCars(id:Int): carDetails {
        return SafeRequest{
            api.GetDetailsCar(id)
        }}


}