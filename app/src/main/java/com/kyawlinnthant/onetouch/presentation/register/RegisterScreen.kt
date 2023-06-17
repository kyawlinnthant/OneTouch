package com.kyawlinnthant.onetouch.presentation.register

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kyawlinnthant.onetouch.R
import com.kyawlinnthant.onetouch.common.Constant
import com.kyawlinnthant.onetouch.common.ImageSize
import com.kyawlinnthant.onetouch.common.asSize
import com.kyawlinnthant.onetouch.common.getTmpFileUri
import com.kyawlinnthant.onetouch.theme.OneTouchTheme
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen() {
    val vm: RegisterViewModel = hiltViewModel()
    val loading = vm.isLoading.collectAsState()
    val photoUri = vm.tmpUri.collectAsState()

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackState = remember { SnackbarHostState() }

    var tmpUri by remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                tmpUri?.let { uri ->
                    val size = uri asSize context
                    val bmpSize = size asSize ImageSize.MB
                    if (bmpSize >= Constant.IMAGE_SIZE_LIMIT) {
                        scope.launch {
                            snackState.showSnackbar(
                                "Selected photo's size is larger than ${Constant.IMAGE_SIZE_LIMIT} MB"
                            )
                        }
                        return@rememberLauncherForActivityResult
                    }
                    vm.onAction(RegisterAction.UpdateTmpUri(uri))
                } ?: run {
                    scope.launch {
                        snackState.showSnackbar("Can't pick this photo")
                    }
                }
            } else {
                scope.launch {
                    snackState.showSnackbar("Nothing's picked")
                }
            }
        }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->

        uri?.let {
            val size = it asSize context
            val bmpSize = size asSize ImageSize.MB
            if (bmpSize >= Constant.IMAGE_SIZE_LIMIT) {
                scope.launch {
                    snackState.showSnackbar(
                        "Selected photo's size is larger than ${Constant.IMAGE_SIZE_LIMIT} MB"
                    )
                }
                return@rememberLauncherForActivityResult
            }
            vm.onAction(RegisterAction.UpdateTmpUri(it))
        } ?: run {
            scope.launch {
                snackState.showSnackbar("Nothing's picked")
            }
        }
    }
    Scaffold(
        topBar = {
        },
        snackbarHost = {
            SnackbarHost(hostState = snackState)
        }
    ) { innerPadding ->
        RegisterContent(
            paddingValues = innerPadding,
            onRegister = vm::signup,
            isLoading = loading.value,
            onPickFromCamera = {
                tmpUri = getTmpFileUri(
                    context = context,
                    prefix = "hello"
                )
                cameraLauncher.launch(tmpUri)
            },
            onPickFromGallery = {
                galleryLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            },
            tmpUri = photoUri.value
        )
    }
}

@Composable
fun RegisterContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onRegister: (String, String) -> Unit,
    isLoading: Boolean = false,
    onPickFromCamera: () -> Unit,
    onPickFromGallery: () -> Unit,
    tmpUri: Uri
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pwd by remember { mutableStateOf("") }
    var confirmPwd by remember { mutableStateOf("") }
    var shouldShowPickBtn by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Register",
                style = MaterialTheme.typography.displayLarge,
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.theme),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePicker(
                onPick = {
                    shouldShowPickBtn = !shouldShowPickBtn
                },
                onCamera = {
                    onPickFromCamera()
                    shouldShowPickBtn = !shouldShowPickBtn
                },
                onGallery = {
                    onPickFromGallery()
                    shouldShowPickBtn = !shouldShowPickBtn
                },
//                photoBmp = null,
                uri = tmpUri,
                shouldShowPick = shouldShowPickBtn
            )
            Spacer(modifier = modifier.height(8.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Fill name")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            Spacer(modifier = modifier.height(8.dp))

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
            Spacer(modifier = modifier.height(8.dp))
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
            Spacer(modifier = modifier.height(8.dp))
            TextField(
                value = confirmPwd,
                onValueChange = { confirmPwd = it },
                modifier = modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = "Fill password again")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            )
            Spacer(modifier = modifier.height(8.dp))
            Button(
                modifier = modifier.fillMaxWidth(),
                onClick = {
                    onRegister(email, pwd)
                },
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "Register")
            }
            if (isLoading) {
                Text(text = "loading")
            }
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
                isLoading = true,
                onPickFromGallery = {},
                onPickFromCamera = {},
                tmpUri = Uri.EMPTY
            )
        }
    }
}
