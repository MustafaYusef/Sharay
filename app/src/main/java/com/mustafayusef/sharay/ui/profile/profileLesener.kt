package com.mustafayusef.sharay.ui.profile

import com.mustafayusef.sharay.data.models.UserInfo

interface profileLesener {
    fun OnStart()
    fun onSucsess(CarResponse: UserInfo)


    fun onFailer(message:String)
}