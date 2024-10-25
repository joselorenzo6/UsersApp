package com.jm.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.jm.ui.theme.MyAppTheme

@Composable
fun NoInternetScreen() {

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (btn, tv) = createRefs()
        val margin = MyAppTheme.dimens.itemSpacing
        Text("Error", Modifier.constrainAs(tv) {
            top.linkTo(btn.bottom)
            bottom.linkTo(parent.bottom, margin = margin)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        Button(modifier = Modifier.constrainAs(btn) {
            top.linkTo(parent.top)
            bottom.linkTo(tv.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }, onClick = {}) {
            Text("settings")
        }
    }

}
