package com.kyawlinnthant.onetouch.presentation.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kyawlinnthant.onetouch.theme.OneTouchTheme

@Composable
fun RegisterScreen() {
    val vm: RegisterViewModel = hiltViewModel()
    val loading = vm.isLoading.collectAsState()
    Scaffold(
        topBar = {

        }
    ) { innerPadding ->
        RegisterContent(
            paddingValues = innerPadding,
            onRegister = vm::signup,
            isLoading = loading.value
        )
    }
}

@Composable
fun RegisterContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onRegister: (String, String) -> Unit,
    isLoading: Boolean = false,
) {

    var email by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Fill email")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            )
        )
        TextField(
            value = pwd,
            onValueChange = { pwd = it },
            modifier = modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Fill password")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        Button(onClick = {
            onRegister(email, pwd)
        }) {
            Text(text = "Register")
        }
        if (isLoading) {
            Text(text = "loading")
        }
    }
}

@Preview
@Composable
private fun RegisterPreview() {
    OneTouchTheme {
        Surface {
            RegisterContent(
                paddingValues = PaddingValues(),
                onRegister = { _, _ -> },
                isLoading = true
            )
        }
    }
}
