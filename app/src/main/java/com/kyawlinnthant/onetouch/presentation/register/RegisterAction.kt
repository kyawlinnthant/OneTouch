package com.kyawlinnthant.onetouch.presentation.register

import android.net.Uri

sealed interface RegisterAction{
    data class UpdateTmpUri(val uri : Uri) : RegisterAction
}