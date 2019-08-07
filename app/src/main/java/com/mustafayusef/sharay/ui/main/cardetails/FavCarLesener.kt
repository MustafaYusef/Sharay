package com.mustafayusef.sharay.ui.main.cardetails

import com.mustafayusef.sharay.data.models.favorite.addResFav

interface FavCarLesener {

    fun OnStartFav()
    fun onSucsessFav(CarResponse: addResFav)




    fun onFailerFav(message:String)
}