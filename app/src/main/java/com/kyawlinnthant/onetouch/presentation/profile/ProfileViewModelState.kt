package com.kyawlinnthant.onetouch.presentation.profile

import com.kyawlinnthant.onetouch.data.ds.CurrentUser

data class ProfileViewModelState(
    val currentUser: CurrentUser = CurrentUser()
){
    fun user() = currentUser
}
