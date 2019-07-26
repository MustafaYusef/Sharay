package com.mustafayusef.sharay.ui.sections.carRent

import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCarDetails

interface rentLesener{

    fun OnStart()
    fun onSucsess(CarResponse: CarsModel)

    fun onFailer(message:String)
}