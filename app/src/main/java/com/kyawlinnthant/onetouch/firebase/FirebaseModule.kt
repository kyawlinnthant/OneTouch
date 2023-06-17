package com.kyawlinnthant.onetouch.firebase

import android.app.Application
import android.content.Context
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kyawlinnthant.onetouch.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class SignInRequest

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class SignupRequest

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseFireStore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideOneTabClient(
        @ApplicationContext context: Context
    ): SignInClient = Identity.getSignInClient(context)

    @Provides
    @Singleton
    @SignInRequest
    fun provideSignInRequest(
        app: Application
    ): BeginSignInRequest {
        val id = app.getString(R.string.default_web_client_id)
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(id)
                    .setFilterByAuthorizedAccounts(false) // true for remember
                    .build()
            )
            .setAutoSelectEnabled(false) // true for remember
            .build()
    }

    @Provides
    @Singleton
    @SignupRequest
    fun provideSignupRequest(
        app: Application
    ): BeginSignInRequest {
        val id = app.getString(R.string.default_web_client_id)
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(id)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            ).build()
    }

    @Provides
    @Singleton
    fun provideGoogleSignInOptions(
        app: Application
    ): GoogleSignInOptions {
        val id = app.getString(R.string.default_web_client_id)
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(id)
            .requestEmail()
            .build()
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ): GoogleSignInClient = GoogleSignIn.getClient(app, options)
}
