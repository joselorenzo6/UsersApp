package com.jm.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun LoaderComponent(){
    Box(Modifier.padding(24.dp).fillMaxSize(), contentAlignment = Alignment.Center){
        CircularProgressIndicator(Modifier.fillMaxSize(), strokeWidth = 5.dp)
    }

}