package com.mustafayusef.sharay.ui.profile.myFavarote


import com.mustafayusef.sharay.data.models.favorite.favoriteModel

interface MyFavLesener {

    fun OnStartFav()
    fun onSucsessFav(CarResponse: List<favoriteModel>)




    fun onFailerFav(message:String)
}