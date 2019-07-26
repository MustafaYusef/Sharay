package com.mustafayusef.sharay.data.networks

import com.mustafayusef.holidaymaster.networks.networkIntercepter
import com.mustafayusef.sharay.data.models.*
import com.mustafayusef.sharay.data.networks.repostorys.SignUpAuth
import com.mustafayusef.sharay.data.networks.repostorys.UserAuth
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.Collections.singletonList
import okhttp3.CipherSuite
import okhttp3.TlsVersion
import retrofit2.Response
import retrofit2.http.*
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*
import javax.security.cert.CertificateException
import javax.xml.datatype.DatatypeConstants.SECONDS





interface myApi {

   // @FormUrlEncoded
   @Headers("Content-Type: application/json")
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
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
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