package com.jm.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
class Dimensions(
    val itemSpacing: Dp,
    val viewSpacing: Dp,
    val screenSpacing: Dp,
    val strokeWidth: Dp,
    val cardStrokeWidth:Dp,
    val shadow: Dp
)

val defaultDimensions = Dimensions(
    itemSpacing = 4.dp,
    viewSpacing = 8.dp,
    screenSpacing = 16.dp,
    strokeWidth = 8.dp,
    cardStrokeWidth = 2.dp,
    shadow = 4.dp
)

val largeDimensions = Dimensions(
    itemSpacing = 8.dp,
    viewSpacing = 16.dp,
    screenSpacing = 32.dp,
    strokeWidth = 10.dp,
    cardStrokeWidth = 4.dp,
    shadow = 8.dp

)