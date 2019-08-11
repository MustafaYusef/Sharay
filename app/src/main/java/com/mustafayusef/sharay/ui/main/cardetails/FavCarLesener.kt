package com.mustafayusef.sharay.ui.main.cardetails

import com.mustafayusef.sharay.data.models.favorite.addResFav
import com.mustafayusef.sharay.data.models.userModels.Favorite
import com.mustafayusef.sharay.data.networks.delete

interface FavCarLesener {

    fun OnStartFav()
    fun onSucsessFav(CarResponse: addResFav)

    //fun onSucsessFav(CarResponse: addResFav)
    fun onSucsessDel(CarResponse: delete)

    fun onFailerFav(message:String)

    fun OnStartProfile()
    fun onSucsessProfile(CarResponse: List<Favorite>)

    //fun onSucsessFav(CarResponse: addResFav)


    fun onFailerProfile(message:String)
}