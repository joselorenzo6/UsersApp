package com.jm.ui.bars

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.jm.ui.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarComponent(
    userName: String? = null,
    showBackButton: Boolean = false,
    onBackClick: (() -> Unit)? = null
) {
    val title = userName?.let {
        stringResource(R.string.toolbar_title_user, it)
    } ?: run {
        stringResource(R.string.toolbar_title)
    }
    TopAppBar(title = { Text(title) }, navigationIcon = {
        if (showBackButton && onBackClick != null) {

            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Atrás"
                )
            }

        } else null
    })
}

