package com.kyawlinnthant.onetouch.presentation.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kyawlinnthant.onetouch.R
import com.kyawlinnthant.onetouch.theme.OneTouchTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PhotoPickerButton(
    modifier: Modifier = Modifier,
    shouldShowPick: Boolean = true,
    onPick: () -> Unit = {},
    onCamera: () -> Unit = {},
    onGallery: () -> Unit = {},
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        AnimatedVisibility(
            visible = !shouldShowPick,
            enter = fadeIn() + slideInHorizontally(initialOffsetX = { -50 }),
            exit = fadeOut() + slideOutHorizontally(targetOffsetX = { -50 })
        ) {
            OutlinedIconButton(onClick = onCamera) {
                Icon(painter = painterResource(id = R.drawable.camera), contentDescription = null)
            }
        }


        AnimatedVisibility(
            visible = shouldShowPick,

            enter = fadeIn() + scaleIn(),
            exit = fadeOut() + shrinkOut(shrinkTowards = Alignment.Center) + scaleOut()
        ) {
            OutlinedButton(onClick = onPick) {
                Text(text = "Pick")
            }
        }


        AnimatedVisibility(
            visible = !shouldShowPick,
            enter = fadeIn() + slideInHorizontally(initialOffsetX = { 50 }),
            exit = fadeOut() + slideOutHorizontally(targetOffsetX = { 50 })
        ) {
            OutlinedIconButton(
                onClick = onGallery,
            ) {
                Icon(painter = painterResource(id = R.drawable.gallery), contentDescription = null)
            }
        }

    }


}

@Composable
@Preview
private fun PhotoPickerButtonPreview() {
    OneTouchTheme {
        Surface {
            PhotoPickerButton()
        }
    }
}