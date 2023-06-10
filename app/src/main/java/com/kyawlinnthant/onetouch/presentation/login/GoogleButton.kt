package com.kyawlinnthant.onetouch.presentation.login

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kyawlinnthant.onetouch.R
import com.kyawlinnthant.onetouch.theme.OneTouchTheme

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    text: String = "Sign in with Google",
    loadingText: String = "Signing In...",
    onClick: () -> Unit = {},
    enabled : Boolean = true,
    loading : Boolean = false,
) {
    val btnText = if (loading) loadingText else text
    OutlinedButton(
        onClick = onClick,
        enabled = enabled
    ) {
        Row(
            modifier = modifier
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = modifier.size(24.dp),
                painter = painterResource(id = R.drawable.google),
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = modifier.width(8.dp))
            Text(text = btnText, textAlign = TextAlign.Center)
            if (loading) {
                Spacer(modifier = modifier.width(8.dp))
                CircularProgressIndicator(
                    modifier = modifier.size(16.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
@Preview
private fun GoogleButtonPreview() {
    OneTouchTheme {
        Surface {
            GoogleButton()
        }
    }
}