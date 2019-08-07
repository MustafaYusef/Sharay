package com.mustafayusef.sharay.ui.search

import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.database.entitis.latestCar

interface searchLesener {
    fun onFailerSerach(message:String)
    fun onSuccessSearch(searchResult:CarsModel)
    fun onSuccessDatabase(searchResult:List<latestCar>)
    fun onSuccessDatabasesave(message: String)
    fun onStartSearch()

}