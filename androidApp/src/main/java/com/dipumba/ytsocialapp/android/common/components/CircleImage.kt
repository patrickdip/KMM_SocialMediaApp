package com.dipumba.ytsocialapp.android.common.components

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.dipumba.ytsocialapp.android.R

@Composable
fun CircleImage(
    modifier: Modifier,
    url: String?,
    uri: Uri? = null,
    onClick: () -> Unit
) {
    AsyncImage(
        model = uri ?: url,
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() },
        placeholder = if (MaterialTheme.colors.isLight) {
            painterResource(id = R.drawable.light_image_place_holder)
        } else {
            painterResource(id = R.drawable.dark_image_place_holder)
        },
        contentScale = ContentScale.Crop,
    )
}