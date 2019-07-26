package com.mustafayusef.sharay.ui.auth

import com.mustafayusef.sharay.data.models.signInModel
import com.mustafayusef.sharay.data.models.signUp

interface AuthLesener {

    fun OnStart()
    fun onSucsess(loginResponse: signUp)
    fun onSucsessSignIn(loginResponse: signInModel)
    fun onFailer(message:String)
}