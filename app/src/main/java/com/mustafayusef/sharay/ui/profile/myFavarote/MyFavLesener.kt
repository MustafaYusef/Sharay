package com.mustafayusef.sharay.ui.profile.myFavarote


import com.mustafayusef.sharay.data.models.favorite.favoriteModel
import com.mustafayusef.sharay.data.models.userModels.UserInfo

interface MyFavLesener {

    fun OnStartFav()
    fun onSucsessFav(CarResponse: List<favoriteModel>)




    fun onFailerFav(message:String)
    fun onFailerNet(message:String)

}