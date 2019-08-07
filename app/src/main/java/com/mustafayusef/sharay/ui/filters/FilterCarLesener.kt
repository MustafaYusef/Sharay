package com.mustafayusef.sharay.ui.filters

import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCarDetails
import com.mustafayusef.sharay.data.models.banners

interface FilterCarLesener {

    fun OnStart()
    fun onSucsess(CarResponse: CarsModel)
    fun onFailer(message:String)
}