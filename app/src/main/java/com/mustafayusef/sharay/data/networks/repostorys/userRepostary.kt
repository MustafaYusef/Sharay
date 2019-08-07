package com.mustafayusef.sharay.data.networks.repostorys


import com.mustafayusef.holidaymaster.networks.SafeApiRequest
import com.mustafayusef.sharay.data.models.UserInfo
import com.mustafayusef.sharay.data.models.addRes
import com.mustafayusef.sharay.data.models.favorite.addResFav
import com.mustafayusef.sharay.data.models.signInModel
import com.mustafayusef.sharay.data.models.signUp
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


    suspend fun AddCar(title: String,
                       brand: String,
                       `class`: String,
                       status: String,
                       year: Int,
                       warid: String,
                       mileage: Int,
                       price: Int,
                       gear: String,
                       cylinders: Int,
                       driveSystem: String,
                       roof: String,
                       seats: Int,
                       type: String,
                       window: String,
                       airBags: String,
                       color: String,
                       description: String,
                       name: String,
                       phone: String,
                       location: String,
                       state: String,
                       date: String,
                       userId: Int,
                       storeId: Int,
                       active: Boolean,
                       isRent: Boolean,
                       isImported: Boolean,
                       image: MultipartBody.Part): addRes {

        return SafeRequest{

            api.AddCar( title,
                 brand,
               `class`,
               status,
             year,
               warid,
               mileage,
                price,
               gear,
                 cylinders,
                 driveSystem,
            roof,
                seats,
              type,
                window,
                airBags,
             color,
               description,
               name,
              phone,
                 location,
                state,
              date,
              userId,
               storeId,
                 active,
               isRent,
                isImported,
             image)
        }}
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