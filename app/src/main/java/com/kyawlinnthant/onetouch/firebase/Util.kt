package com.kyawlinnthant.onetouch.firebase

import com.google.firebase.auth.FirebaseUser
import com.kyawlinnthant.onetouch.data.ds.CurrentUser

fun FirebaseUser.asUser(): CurrentUser {
    return CurrentUser(
        id = this.uid,
        name = this.displayName ?: "",
        email = this.email ?: "",
        photo = this.photoUrl?.toString() ?: "",
    )
}