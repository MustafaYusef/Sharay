package com.mustafayusef.sharay.database.entitis

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class latestCar (
    @PrimaryKey(autoGenerate = true)
    var idDb:Int=0,
//    var `class`: String?=null,
//    var active: Boolean?=null,
//    var airBags: String?=null,
//    var brand: String?=null,
//    var color: String?=null,
//    var cylinders: Int?=null,
//    var date: String?=null,
//    var description: String?=null,
//    var driveSystem: String?=null,
//    var fuel: String?=null,
//    var gear: String?=null,
    var id: Int?=null,
    var image: String?=null,
////    var isRent: String?=null,
//    var location: String?=null,
//    var mileage: Int?=null,
//    var name: String?=null,
//    var phone: String?=null,
//    var price: Int?=null,
//    var roof: String?=null,
//    var seats: String?=null,
//    var status: String?=null,
//    var storeId: Int?=null,
    var title: String?=null
//    var type: String?=null,
//    var userId: Int?=null,
//    var warid: String?=null,
//    var window: String?=null,
//    var year: String?=null
)