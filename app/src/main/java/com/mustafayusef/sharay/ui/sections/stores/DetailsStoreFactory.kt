package com.mustafayusef.sharay.ui.sections.stores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.sharay.data.networks.repostorys.SectionRepostary

class DetailsStoreFactory(val repostary: SectionRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsStoreViewModel(repostary) as T
    }
}