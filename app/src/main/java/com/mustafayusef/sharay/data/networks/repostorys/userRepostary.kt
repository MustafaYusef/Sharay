package com.mustafayusef.sharay.data.networks.repostorys

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mustafayusef.holidaymaster.networks.SafeApiRequest
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.UserInfo
import com.mustafayusef.sharay.data.models.signInModel
import com.mustafayusef.sharay.data.models.signUp
import com.mustafayusef.sharay.data.networks.myApi
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field

class userRepostary(val api:myApi ):SafeApiRequest(){
   suspend fun getUserDate(email:String, password:String):signUp{
       val Auth=UserAuth(email,password)
        return SafeRequest{

            api.getLogin(Auth)
        }}

    suspend fun getSignUpDate(email:String, password:String,phone:String, name:String):signInModel{
        val Auth=SignUpAuth(email,password,phone,name)
        return SafeRequest{

            api.GetSignUp(Auth)
        }}

    suspend fun getUserInfo(token:String): UserInfo {

        return SafeRequest{

            api.getUserInfo(token)
        }}

    }

data class UserAuth(
    var email:String,
    var password:String
)

data class SignUpAuth(
    var email:String,
    var password:String,
    var phone:String,
    var name:String
)