package com.kyawlinnthant.onetouch.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kyawlinnthant.onetouch.theme.OneTouchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            OneTouchTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    OneTouchGraph()
                }
            }
        }
    }
}

@Composable
fun OneTap() {

//    val vm: LoginViewModel = hiltViewModel()

//    val launcher =
//        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult()) { result ->
//            if (result.resultCode == RESULT_OK) {
//                try {
//                    val credentials = vm.client.getSignInCredentialFromIntent(result.data)
//                    val googleIdToken = credentials.googleIdToken
//                    val googleCredentials = getCredential(googleIdToken, null)
//                    vm.firebaseSignIn(googleCredentials)
//                } catch (e: ApiException) {
//                    println(e)
//                }
//            }
//        }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(
            onClick = {}
//            onClick = vm::oneTapSignIn
        ) {


//            val data = vm.oneTapSignInResponse.data
//            data?.let {
//                val intent = IntentSenderRequest.Builder(data.pendingIntent.intentSender).build()
//                launcher.launch(intent)
//            }

        }
    }
}

