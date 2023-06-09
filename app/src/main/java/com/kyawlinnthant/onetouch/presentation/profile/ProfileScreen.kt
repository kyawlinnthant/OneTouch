package com.kyawlinnthant.onetouch.presentation.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.kyawlinnthant.onetouch.data.ds.CurrentUser
import com.kyawlinnthant.onetouch.theme.OneTouchTheme

@Composable
fun ProfileScreen() {
    val vm: ProfileViewModel = hiltViewModel()
    val user = vm.currentUser.collectAsState()
    Scaffold(topBar = {}) { innerPadding ->
        ProfileContent(
            paddingValues = innerPadding,
            user = user.value
        )
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    user: CurrentUser
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "${user.id} : ${user.name.ifEmpty { user.email }} : ${user.email}")
    }
}

@Preview
@Composable
private fun ProfilePreview() {
    OneTouchTheme {
        Surface {
            ProfileContent(
                paddingValues = PaddingValues(), user = CurrentUser(
                    id = "this is id",
                    name = "this is name",
                    email = "this is email",
                    photo = "this is photo"
                )
            )
        }
    }
}