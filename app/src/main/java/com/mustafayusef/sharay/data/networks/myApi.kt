package com.mustafayusef.sharay.data.networks

import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.sharay.data.models.*

import com.mustafayusef.sharay.data.models.favorite.addResFav
import com.mustafayusef.sharay.data.models.favorite.favoriteModel
import com.mustafayusef.sharay.data.models.sections.*
import com.mustafayusef.sharay.data.models.userModels.UserInfo
import com.mustafayusef.sharay.data.networks.repostorys.SignUpAuth
import com.mustafayusef.sharay.data.networks.repostorys.UserAuth
import com.mustafayusef.sharay.ui.MainActivity

import okhttp3.*

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Headers
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap


interface myApi {

   // @FormUrlEncoded
   @Multipart
    @POST("car/add")
   suspend fun AddCar(
       @Header("token")token:String,
        @Part("title") title: String,
    @Part("brand") brand: String,
    @Part("class") `class`: String,
    @Part("status") status: String,
    @Part("year") year: Int,
    @Part("warid") warid: String,
    @Part("mileage") mileage: Int,
    @Part("price") price: Int,
    @Part("gear") gear: String,
    @Part("cylinders") cylinders: Int,
        @Part("fuel") fuel: String,
    @Part("driveSystem") driveSystem: String,
    @Part("roof") roof: String,
    @Part("seats") seats: Int,
    @Part("type") type: String,
    @Part("window") window: String,
    @Part("airBags") airBags: String,
    @Part("color") color: String,
    @Part("description") description: String,
    @Part("name") name: String,
    @Part("phone") phone: String,
    @Part("location") location: String,
    @Part("state") state: String,
    @Part("date") date: String,
    @Part("userId") userId: Int,
    @Part("storeId") storeId: Int,
    @Part("active") active: Boolean,
    @Part("isRent") isRent: Boolean,
    @Part("isImported") isImported: Boolean,
    @Part image: MultipartBody.Part
    ): Response<addRes>

    @Multipart
    @POST("car/image")
    suspend fun Images(
        @Part image0: MultipartBody.Part?,
        @Part image1: MultipartBody.Part?,
        @Part image2: MultipartBody.Part?,
        @Part image3: MultipartBody.Part?,
        @Part image4: MultipartBody.Part?,
        @Part image5: MultipartBody.Part?,
        @Part image6: MultipartBody.Part?,
        @Part image7: MultipartBody.Part?,
        @Part image8: MultipartBody.Part?,
        @Part image9: MultipartBody.Part?,
        @Part image10: MultipartBody.Part?,
        @Part("id") id: Int,
        @Header("token") token: String
//        @Query("brand") brand: String?,
//        @Query("class") `class`: String?,
//        @Query("year") year: String?,
//        @Query("warid") warid: String?,
//        @Query("mileage") mileage: String?,
//        @Query("priceMin") priceMin: String?,
//        @Query("priceMax") priceMax: String?,
//        @Query("location") location: String?
    ): Response<delete>


    @POST("cars/filter?")
    suspend fun Filters(
        @Body filter:HashMap<String,String>
//        @Query("brand") brand: String?,
//        @Query("class") `class`: String?,
//        @Query("year") year: String?,
//        @Query("warid") warid: String?,
//        @Query("mileage") mileage: String?,
//        @Query("priceMin") priceMin: String?,
//        @Query("priceMax") priceMax: String?,
//        @Query("location") location: String?
    ): Response<CarsModel>

    @POST("signin")
    suspend fun getLogin(
        @Body user: UserAuth
    ): Response<signUp>

    @Headers("Content-Type: application/json")
    @POST("signup")
    suspend fun GetSignUp(
        @Body user: SignUpAuth
    ): Response<signInModel>

    @GET("cars")
    suspend fun GetCars(): Response<CarsModel>

    @GET("car/{id}")
    suspend fun GetDetailsCar(
        @Path("id")id:Int): Response<carDetails>

    @GET("user")
    suspend fun getUserInfo(@Header("token")token:String): Response<UserInfo>

    @GET("numbers")
    suspend fun getNumbers(): Response<NumbersModel>

    @GET("stores")
    suspend fun getStores(): Response<StoresModel>

    @GET("cars/rent?")
    suspend fun GetRent(): Response<RentModel>

    @GET("cars/imported?")
    suspend fun GetImport(): Response<RentModel>

    @GET("parts")
    suspend fun GetPart(): Response<PartsModel>

    @GET("motors")
    suspend fun GetMotor(): Response<motorModel>

    @GET("motor/{id}")
    suspend fun GetMotorDetails(
        @Path("id")id:Int
    ): Response<motorDetailsModel>

    @GET("banners")
    suspend fun getBanners(): Response<banners>

    @GET("store/{id}")
    suspend fun GetDetailsStore(
        @Path("id")id:Int): Response<StoreDetails>

    @GET("favorite/get?")
    suspend fun GetFavorite(
        @Header("token")token:String): Response<List<favoriteModel>>

    @Multipart
    @POST("favorite?")
    suspend fun AddFavorite(
        @Header("token")token:String,@Part("id") id:Int,@Part("type") type:Int): Response<addResFav>







    @HTTP(method = "DELETE", path = "car/delete", hasBody = true)
    suspend fun DeleteMyCar(
        @Header("token")token:String,@Body id:De): Response<delete>

    @HTTP(method = "DELETE", path = "favorite/delete?", hasBody = true)
    suspend fun DeleteFavorite(
        @Header("token")token:String, @Query("id")id:Int, @Body id1:De): Response<delete>

    companion object{

        operator fun invoke(
            networkIntercepter:networkIntercepter
        ):myApi{
            val spec = ConnectionSpec.Builder ( ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
                )
                .build()
            val spec1 = ConnectionSpec.Builder ( ConnectionSpec.CLEARTEXT)
                .build()


            val client = OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50,TimeUnit.SECONDS)
                .writeTimeout(50,TimeUnit.SECONDS)
                .addInterceptor(networkIntercepter)
                .connectionSpecs(Collections.singletonList(spec))
                .connectionSpecs(Collections.singletonList(spec1))
                .build()
            return Retrofit.Builder()
                .client(client)
                .baseUrl("http://api.centralmarketiq.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())

                .build().create(myApi::class.java)
        }
    }
}
data class delete(

    val `data`: String,
    val errMsg: String
    )
data class De(

    var id:String
)