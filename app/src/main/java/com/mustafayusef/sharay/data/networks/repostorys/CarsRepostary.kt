package com.mustafayusef.sharay.data.networks.repostorys


import com.mustafayusef.holidaymaster.networks.SafeApiRequest
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.addRes
import com.mustafayusef.sharay.data.models.banners
import com.mustafayusef.sharay.data.models.carDetails
import com.mustafayusef.sharay.data.models.favorite.addResFav
import com.mustafayusef.sharay.data.models.favorite.favoriteModel
import com.mustafayusef.sharay.data.models.userModels.UserInfo
import com.mustafayusef.sharay.data.networks.De
import com.mustafayusef.sharay.data.networks.delete
import com.mustafayusef.sharay.data.networks.myApi

import com.mustafayusef.sharay.ui.MainActivity
import com.mustafayusef.sharay.ui.auth.signup.Login
import com.mustafayusef.sharay.ui.profile.Profile_fragment
import okhttp3.MultipartBody


class CarsRepostary(val api:myApi ):SafeApiRequest(){
    suspend fun getCars():CarsModel{
        return SafeRequest{
            api.GetCars()
        }}

    suspend fun getFilter(brand: String?,
                          `class`: String?,
                          year: String?,
                          warid: String?,
                          mileage: String?,
                          priceMin: String?,
                          priceMax:String?,
                          gear: String?):CarsModel{
        val filter=HashMap<String,String>()
        `class`?.let { filter.put("class", it) }
        brand?.let { filter.put("brand", it) }
        year?.let { filter.put("year", it) }
        warid?.let { filter.put("warid", it) }
        mileage?.let { filter.put("mileage", it) }
        priceMin?.let { filter.put("priceMin", it) }
        priceMax?.let { filter.put("priceMax", it) }
        gear?.let { filter.put("location", it) }
        return SafeRequest{
            api.Filters(filter)
        }}

    suspend fun images(image:List<MultipartBody.Part> ,id:Int):delete{
        val filter=HashMap<Int,MultipartBody.Part>()
        for (i in 0 until image.size) {

            image.get(i)?.let { filter.put(i, it) }
            image.get(i)?.let { filter.put(i, it) }
            image.get(i)?.let { filter.put(i, it) }
            image.get(i)?.let { filter.put(i, it) }
            image.get(i)?.let { filter.put(i, it) }
            image.get(i)?.let { filter.put(i, it) }
            image.get(i)?.let { filter.put(i, it) }
            image.get(i)?.let { filter.put(i, it) }
            image.get(i)?.let { filter.put(i, it) }
            image.get(i)?.let { filter.put(i, it) }
            image.get(i)?.let { filter.put(i, it) }
        }
        return SafeRequest{
            api.Images(filter[0],filter[1],filter[2],filter[3]
                ,filter[4],filter[5],filter[6],filter[7],filter[8],filter[9],filter[10],id,MainActivity.cacheObj  .token)
        }}



    suspend fun getDetailsCars(id:Int):carDetails{
        return SafeRequest{
            api.GetDetailsCar(id)
        }}

    suspend fun getUserIn(token:String): UserInfo {

        return SafeRequest{

            api.getUserInfo(token)
        }}

    suspend fun getBanners():banners{
        return SafeRequest{
            api.getBanners()
        }}

    suspend fun GetFavorite(token: String):List<favoriteModel>{
        return SafeRequest {
            api.GetFavorite(token)
        }
    }

    suspend fun AddFavorite(token: String,id:Int,type:Int): addResFav {
        return SafeRequest {
            api.AddFavorite(token,id,type)
        }
    }



    suspend fun AddCar( title: String,
                        brand: String,
                        `class`: String,
                      status: String,
                        year: Int,
                       warid: String,
                       mileage: Int,
                        price: Int,
                         gear: String,
                         cylinders: Int,
                        fuel:String,
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
        return SafeRequest {
            api.AddCar(MainActivity.cacheObj .token,
                title,
                brand,
                `class`,
                status,
                year,
                warid,
                mileage,
                price,
                gear,
                cylinders,
                fuel,
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
        }
    }
    suspend fun deleteFav(token: String,id:Int,FavId:Int): delete {
        var a= De(id=FavId.toString())
        return SafeRequest {
            api.DeleteFavorite(token,id,a)
        }
    }

}
