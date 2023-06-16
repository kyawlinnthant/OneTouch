package com.kyawlinnthant.onetouch.presentation.register

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kyawlinnthant.onetouch.theme.OneTouchTheme

@Composable
fun ProfilePicker(
    modifier: Modifier = Modifier,
    onPick: () -> Unit = {},
    onCamera: () -> Unit = {},
    onGallery: () -> Unit = {},
    photoUrl: String = "",
    photoBmp: Bitmap? = null,
    shouldShowPick: Boolean = true,
) {
    Box(
        modifier = modifier
            .size(180.dp)
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,
    ) {

        if (photoBmp == null && photoUrl.isEmpty()) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary.copy(0.3f))
            )
        } else {
            photoBmp?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxSize()
                )
            } ?: kotlin.run {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxSize()
                )
            }
        }



        PhotoPickerButton(
            shouldShowPick = shouldShowPick,
            onPick = onPick,
            onCamera = onCamera,
            onGallery = onGallery,
        )
    }
}

@Preview
@Composable
private fun ProfilePickerPreview() {
    OneTouchTheme {
        Surface {
            ProfilePicker()
        }
    }
}