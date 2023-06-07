package com.kyawlinnthant.onetouch.presentation

import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.kyawlinnthant.onetouch.R
import com.kyawlinnthant.onetouch.theme.OneTouchTheme

class MainActivity : ComponentActivity() {

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current
            oneTapClient = Identity.getSignInClient(this)
            signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(stringResource(id = R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
//                .setAutoSelectEnabled(true)
                .build()


            OneTouchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Button(onClick = {
                            oneTapClient.beginSignIn(signInRequest).addOnSuccessListener {
                                try {
                                    Log.d("sign.success",it.toString())
                                } catch (e: IntentSender.SendIntentException) {
                                    Log.d("sign.success.catch",e.localizedMessage?.toString()?:"no message")
                                }
                            }.addOnFailureListener {
                                Log.d("sign.fail",it.toString())
                            }
                        }) {
                            Text(text = "Google")
                        }
                    }
                }
            }
        }
    }
}

