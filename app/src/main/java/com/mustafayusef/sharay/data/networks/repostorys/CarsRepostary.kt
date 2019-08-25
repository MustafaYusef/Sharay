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
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Part


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



    suspend fun AddCar( title1: String,
                        brand1: String,
                        class1: String,
                      status1: String,
                        year: Int,
                       warid1: String,
                       mileage: Int,
                        price: Int,
                         gear1: String,
                         cylinders: Int,
                        fuel1:String,
                        driveSystem1: String,
                         roof1: String,
                        seats: Int,
                      type1: String,
                        window1: String,
                        airBags1: String,
                       color1: String,
                         description1: String,
                       name1: String,
                         phone1: String,
                         location1: String,
                         state1: String,
                         date1: String,
                       userId: Int,
                        storeId: Int,
                        active: Boolean,
                         isRent: Boolean,
                       isImported: Boolean,
                         image: MultipartBody.Part): addRes {
        var title= RequestBody.create(MediaType.parse("text/plain"),title1)
        var brand= RequestBody.create(MediaType.parse("text/plain"),brand1)
        var `class`= RequestBody.create(MediaType.parse("text/plain"),class1)
        var status= RequestBody.create(MediaType.parse("text/plain"),status1)
        var warid= RequestBody.create(MediaType.parse("text/plain"),warid1)
        var gear= RequestBody.create(MediaType.parse("text/plain"),gear1)
        var fuel= RequestBody.create(MediaType.parse("text/plain"),fuel1)
        var driveSystem= RequestBody.create(MediaType.parse("text/plain"),driveSystem1)
        var roof= RequestBody.create(MediaType.parse("text/plain"),roof1)

        var type= RequestBody.create(MediaType.parse("text/plain"),type1)
        var window= RequestBody.create(MediaType.parse("text/plain"),window1)
        var airBags= RequestBody.create(MediaType.parse("text/plain"),airBags1)
        var color= RequestBody.create(MediaType.parse("text/plain"),color1)

        var description= RequestBody.create(MediaType.parse("text/plain"),description1)

        var name= RequestBody.create(MediaType.parse("text/plain"),name1)
        var phone= RequestBody.create(MediaType.parse("text/plain"),phone1)
        var location= RequestBody.create(MediaType.parse("text/plain"),location1)
        var state= RequestBody.create(MediaType.parse("text/plain"),state1)
        var date= RequestBody.create(MediaType.parse("text/plain"),date1)




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
