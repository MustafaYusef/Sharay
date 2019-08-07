package com.mustafayusef.sharay.database

import androidx.room.*
import com.mustafayusef.sharay.data.models.DataCars
import com.mustafayusef.sharay.database.entitis.latestCar
import retrofit2.Response



@Dao
interface Car_Dao {
    @Query("select * from latestCar")
    suspend fun getAllCar():List<latestCar>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertCar(cars: latestCar):Long

//  @Query("delete from latestCar where id=:id")
//   fun deletsurvayById(id: Int?):Int

    @Query("DELETE FROM latestCar where idDb NOT IN (SELECT idDb from latestCar ORDER BY idDb DESC LIMIT 10)")
    suspend fun deleteCars():Int

//    @Query("DELETE FROM survayRequest")
//    suspend fun deleteAllSurvay()
}