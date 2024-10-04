package com.jm.ui.screen

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jm.ui.R

@Composable
fun UserImage(
    userImageUrl: String, modifier: Modifier = Modifier.size(100.dp).clip(CircleShape)
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(userImageUrl)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.img_no_available),
        contentDescription = "contentDescription",
        contentScale = ContentScale.Crop,
        modifier = modifier
        )



}