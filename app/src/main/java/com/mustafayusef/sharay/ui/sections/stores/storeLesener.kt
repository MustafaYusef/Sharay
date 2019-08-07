package com.mustafayusef.sharay.ui.sections.stores

import com.mustafayusef.sharay.data.models.sections.StoreDetails

interface storeLesener {
    fun onFailer(message:String)
    fun onSeccess(message:StoreDetails)
    fun OnStart()
}