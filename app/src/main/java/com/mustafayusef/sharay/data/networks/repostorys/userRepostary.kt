package com.mustafayusef.sharay.data.networks.repostorys


import com.mustafayusef.holidaymaster.networks.SafeApiRequest

import com.mustafayusef.sharay.data.models.addRes
import com.mustafayusef.sharay.data.models.favorite.addResFav
import com.mustafayusef.sharay.data.models.signInModel
import com.mustafayusef.sharay.data.models.signUp
import com.mustafayusef.sharay.data.models.userModels.UserInfo
import com.mustafayusef.sharay.data.networks.De
import com.mustafayusef.sharay.data.networks.delete
import com.mustafayusef.sharay.data.networks.myApi
import okhttp3.MultipartBody


class userRepostary(val api:myApi ):SafeApiRequest(){
   suspend fun getUserDate(email:String, password:String):signUp{
       val Auth=UserAuth(email,password)
            return SafeRequest{

            api.getLogin(Auth)
        }}

    suspend fun getSignUpDate(email:String, password:String,phone:String,phoneSecond:String, name:String):signInModel{
      var Auth:SignUpAuth?=null
       if(phoneSecond==""){

           Auth=SignUpAuth(email,password,phone,phone,name)
       }else{
           Auth=SignUpAuth(email,password,phone,phoneSecond,name)

       }


        return SafeRequest{

            api.GetSignUp(Auth)
        }}

    suspend fun getUserInfo(token:String): UserInfo {

        return SafeRequest{

            api.getUserInfo(token)
        }}
    suspend fun deletMyCar(token:String,carId:Int): delete {
       var a= De(id=carId.toString())
        return SafeRequest{

            api.DeleteMyCar(token,a)
        }}

//
    }

data class UserAuth(
    var phone:String,
    var password:String
)

data class SignUpAuth(
    var email:String,
    var password:String,
    var phone:String,
    var phoneSecond:String?=null,
    var name:String
)

data class SignUpAuthNoSecond(
    var email:String,
    var password:String,
    var phone:String,

    var name:String
)