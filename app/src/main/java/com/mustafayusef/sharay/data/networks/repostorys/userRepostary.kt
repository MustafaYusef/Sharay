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

    suspend fun getSignUpDate(email:String?, password:String,phone:String,phoneSecond:String?, name:String):signInModel{
        val filter=HashMap<String,String>()
        email?.let { filter.put("email", it) }
        password?.let { filter.put("password", it) }
        phoneSecond?.let { filter.put("phoneSecond", it) }
        name?.let { filter.put("name", it) }
        phone?.let { filter.put("phone", it) }




        return SafeRequest{

            api.GetSignUp(filter)
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