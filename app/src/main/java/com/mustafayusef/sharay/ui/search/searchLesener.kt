package com.mustafayusef.sharay.ui.search

interface searchLesener {
    fun onFailerSerach(message:String)
    fun onSuccessSearch()
    fun onStartSearch()
}