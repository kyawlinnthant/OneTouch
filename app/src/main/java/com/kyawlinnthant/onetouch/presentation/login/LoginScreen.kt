package com.kyawlinnthant.onetouch.presentation.login

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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.kyawlinnthant.onetouch.theme.OneTouchTheme

@Composable
fun LoginScreen() {
    Scaffold(
        topBar = {

        },

        ) { innerPadding ->
        LoginContent(paddingValues = innerPadding)
    }
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues
) {
    var email by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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
        Button(onClick = {}) {
            Text(text = "Login")
        }
        TextButton(onClick = {  }) {
            Text(text = "Register")
        }
        TextButton(onClick = {  }) {
            Text(text = "Forgot password")
        }
        Button(onClick = {}) {
            Text(text = "One Tap Login")
        }
    }
}

@Composable
@Preview
private fun LoginContentPreview() {
    OneTouchTheme {
        Surface {
            LoginContent(paddingValues = PaddingValues())
        }
    }
}