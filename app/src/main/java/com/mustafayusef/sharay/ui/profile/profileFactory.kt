package com.mustafayusef.sharay.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mustafayusef.sharay.data.networks.repostorys.userRepostary

class profileFactory  (val repostary: userRepostary) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileFragmentViewModel(repostary) as T
    }

}

