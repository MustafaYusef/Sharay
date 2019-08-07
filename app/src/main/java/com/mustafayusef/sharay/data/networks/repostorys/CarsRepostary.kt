package com.mustafayusef.sharay.data.networks.repostorys


import com.mustafayusef.holidaymaster.networks.SafeApiRequest
import com.mustafayusef.sharay.data.models.CarsModel
import com.mustafayusef.sharay.data.models.addRes
import com.mustafayusef.sharay.data.models.banners
import com.mustafayusef.sharay.data.models.carDetails
import com.mustafayusef.sharay.data.models.favorite.addResFav
import com.mustafayusef.sharay.data.models.favorite.favoriteModel
import com.mustafayusef.sharay.data.networks.delete
import com.mustafayusef.sharay.data.networks.myApi
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

    suspend fun getDetailsCars(id:Int):carDetails{
        return SafeRequest{
            api.GetDetailsCar(id)
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

    suspend fun DeleteFavorite(token: String,idUser:Int,idCarFav:Int): delete {
        return SafeRequest {
            api.DeleteFavorite(token,idUser,idCarFav)
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

}
