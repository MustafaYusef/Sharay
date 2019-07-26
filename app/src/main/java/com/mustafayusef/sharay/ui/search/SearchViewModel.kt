package com.mustafayusef.sharay.ui.search

import android.view.View
import androidx.lifecycle.ViewModel;

class SearchViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    var searchText:String?=null

    var litsener:searchLesener?=null
    fun doSearch(view: View){
        litsener?.onStartSearch()
       if(searchText.isNullOrEmpty()){
           litsener?.onFailerSerach("the text is empty")

       }
        litsener?.onSuccessSearch()
    }
}
