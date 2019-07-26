package com.mustafayusef.sharay.data.networks.repostorys

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mustafayusef.holidaymaster.networks.SafeApiRequest
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.carDetails
import com.mustafayusef.sharay.data.networks.myApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarsRepostary(val api:myApi ):SafeApiRequest(){
    suspend fun getCars():CarsModel{
        return SafeRequest{
            api.GetCars()
        }}

    suspend fun getDetailsCars(id:Int):carDetails{
        return SafeRequest{
            api.GetDetailsCar(id)
        }}
}