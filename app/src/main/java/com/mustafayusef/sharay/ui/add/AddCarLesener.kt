package com.mustafayusef.sharay.ui.add

import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCarDetails
import com.mustafayusef.sharay.data.models.addRes
import com.mustafayusef.sharay.data.models.banners

interface AddCarLesener {

    fun OnStart()
    fun onSucsess(CarResponse: addRes)
    fun onFailer(message:String)
}