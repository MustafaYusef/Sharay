package com.mustafayusef.sharay.ui.main

import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCarDetails

interface MainCarLesener {

    fun OnStart()
    fun onSucsess(CarResponse: CarsModel)
    fun onSucsessDetails(CarResponse: DataCarDetails)
    fun onFailer(message:String)
}