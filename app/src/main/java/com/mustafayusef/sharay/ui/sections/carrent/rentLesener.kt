package com.mustafayusef.sharay.ui.sections.carrent

import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.carData
import com.mustafayusef.sharay.data.models.sections.*

interface rentLesener{

    fun OnStart()
    fun onSucsessNumbers(CarResponse: NumbersModel)
    fun onSucsessRent(CarResponse: RentModel)
    fun onSucsessStores(CarResponse: StoresModel)

    fun onSucsessCars(CarResponse: CarsModel)
    fun onSucsessParts(CarResponse: PartsModel)
    fun onSucsessImport(CarResponse: RentModel)
    fun onSucsessMotor(CarResponse: motorModel)
    fun OnFailer(message:String)
}