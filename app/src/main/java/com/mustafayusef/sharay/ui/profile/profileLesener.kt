package com.mustafayusef.sharay.ui.profile


import com.mustafayusef.sharay.data.models.signUp
import com.mustafayusef.sharay.data.models.userModels.UserInfo
import com.mustafayusef.sharay.data.networks.delete

interface profileLesener {
    fun OnStart()
    fun onSucsess(CarResponse: UserInfo)

    fun onSucsessLog(loginResponse: signUp)
    fun onFailer(message:String)


    fun OnStartDeletCar()
    fun onSucsessDeletCar(CarResponse: delete)


    fun onFailerDeletCar(message:String)
}