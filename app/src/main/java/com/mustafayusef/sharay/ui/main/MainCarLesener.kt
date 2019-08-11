package com.mustafayusef.sharay.ui.main

import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.DataCarDetails
import com.mustafayusef.sharay.data.models.banners

interface MainCarLesener {

    fun OnStart()
    fun onSucsess(CarResponse: CarsModel)
    fun onSucsessDetails(CarResponse: DataCarDetails)
    fun onSucsessBanners(CarResponse: banners)

    fun onComplete(
        carsResponse: CarsModel,
        bannerResponse: banners
    )

    fun onFailer(message:String)
}