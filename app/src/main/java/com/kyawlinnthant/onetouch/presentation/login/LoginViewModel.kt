package com.kyawlinnthant.onetouch.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInResult
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.AuthCredential
import com.kyawlinnthant.onetouch.common.DataResult
import com.kyawlinnthant.onetouch.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repo: Repository,
    val client: SignInClient
) : ViewModel() {

    var oneTapSignInResponse by mutableStateOf<DataResult<BeginSignInResult?>>(
        DataResult.Success(
            null
        )
    )
        private set
    var signInWithGoogleResponse by mutableStateOf<DataResult<Boolean>>(DataResult.Success(false))
        private set

    fun oneTapSignIn() {
        viewModelScope.launch {
            oneTapSignInResponse = repo.oneTapSignIn()
        }
    }

    fun firebaseSignIn(credential: AuthCredential) {
        viewModelScope.launch {
            signInWithGoogleResponse = repo.firebaseSignIn(credential)
        }
    }

}