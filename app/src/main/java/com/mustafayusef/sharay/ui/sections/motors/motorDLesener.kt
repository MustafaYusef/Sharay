package com.mustafayusef.sharay.ui.sections.motors

import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.carData
import com.mustafayusef.sharay.data.models.sections.*

interface motorDLesener{

    fun OnStart()
    fun onSucsess(CarResponse: motorDataDetails)

    fun onFailer(message:String)
}