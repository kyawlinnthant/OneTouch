package com.kyawlinnthant.onetouch.presentation.login

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kyawlinnthant.onetouch.theme.OneTouchTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val vm: LoginViewModel = hiltViewModel()
    val loading = vm.isLoading.collectAsState()
    val errorEmail = vm.isErrorEmail.collectAsState()
    val errorPwd = vm.isErrorPwd.collectAsState()
    val googleLoading = vm.googleLoading.collectAsState()
    val googleEnabled = vm.googleEnabled.collectAsState()
    val snackState = remember { SnackbarHostState() }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            vm.getSignInCredential(it.data)
        }else{
            vm.updateGoogleLoading(false)
        }
    }

    LaunchedEffect(key1 = true) {
        vm.uiEvent.collect {
            when (it) {
                is LoginEvent.LaunchGoogleAccounts -> launcher.launch(
                    IntentSenderRequest.Builder(
                        it.result.pendingIntent.intentSender
                    ).build()
                )

                is LoginEvent.Snack -> snackState.showSnackbar(message = it.message)
            }
        }
    }

    if (loading.value) {
        AlertDialog(onDismissRequest = {}) {
            CircularProgressIndicator()
        }
    }

    Scaffold(
        topBar = {

        },
        snackbarHost = {
            SnackbarHost(hostState = snackState)
        }
    ) { innerPadding ->
        LoginContent(
            paddingValues = innerPadding,
            onRegister = {
                vm.onAction(LoginAction.GoRegister)
            },
            onLogin = { email, pwd ->
                vm.onAction(LoginAction.Login(email = email, pwd = pwd))
            },
            onPassword = {
                vm.onAction(LoginAction.GoForgotPassword)
            },
            onGoogleLogin = {
                vm.onAction(LoginAction.GoogleLogin)
            },
            googleLoading = googleLoading.value,
            googleEnabled = googleEnabled.value,
            emailError = errorEmail.value,
            pwdError = errorPwd.value
        )
    }
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onRegister: () -> Unit,
    onPassword: () -> Unit,
    onLogin: (String, String) -> Unit,
    onGoogleLogin: () -> Unit,
    googleLoading: Boolean = false,
    googleEnabled: Boolean = true,
    emailError: Boolean = false,
    pwdError: Boolean = false,
) {
    var email by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }
    val pwdHint =
        " *At least 8 character minimum \n *At most maximum 20 characters \n *At least 1 lowercase alphabet \n *At least 1 uppercase alphabet \n *At least 1 number \n *At least 1 special character (!@#\$%^&+-)"
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(paddingValues),
    ) {
        Text(text = "Login", style = MaterialTheme.typography.displayLarge)
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextField(
                modifier = modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                placeholder = {
                    Text(text = "Fill email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                isError = emailError,
            )

            Spacer(modifier = modifier.height(8.dp))
            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    },
                value = pwd,
                onValueChange = { pwd = it },
                placeholder = {
                    Text(text = "Fill password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                isError = pwdError

            )
            if (isFocused) {

                Text(
                    text = pwdHint,
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Start,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            Spacer(modifier = modifier.height(8.dp))
            Button(
                modifier = modifier.fillMaxWidth(),
                onClick = {
                    onLogin(email, pwd)
                },
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "Login")
            }
            TextButton(onClick = onRegister) {
                Text(text = "Register")
            }
            TextButton(onClick = onPassword) {
                Text(text = "Forgot password")
            }
            Spacer(modifier = modifier.height(24.dp))
            GoogleButton(
                onClick = onGoogleLogin,
                loading = googleLoading,
                enabled = googleEnabled,
            )
        }
    }
}

@Composable
@Preview
private fun LoginContentPreview() {
    OneTouchTheme {
        Surface {
            LoginContent(
                paddingValues = PaddingValues(),
                onLogin = { _, _ -> },
                onRegister = {},
                onPassword = {},
                onGoogleLogin = {},
            )
        }
    }
}