package com.jm.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.jm.ui.R
import com.jm.ui.bars.ToolbarComponent
import com.jm.ui.theme.MyAppTheme
import com.jm.ui.viewmodel.UsersViewmodel

@Composable
fun UserScreen(navController: NavHostController, viewModel: UsersViewmodel) {
    val user = viewModel.selectedUser

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ToolbarComponent(user?.name ?: "", true) { navController.popBackStack() } }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Box(
                modifier = Modifier
                    .shadow(MyAppTheme.dimens.shadow)
                    .weight(2f)
                    .wrapContentSize(),

                ) {
                UserImage(
                    user?.img ?: "",
                    Modifier
                        .fillMaxSize()
                        .align(Alignment.Center)
                        .padding(MyAppTheme.dimens.screenSpacing)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(MyAppTheme.dimens.screenSpacing)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.TopStart)
                        .padding(MyAppTheme.dimens.screenSpacing)
                ) {
                    Text(text = stringResource(R.string.user_name, user?.name ?: "error"), style =  MaterialTheme.typography.titleLarge )
                    Text(text = stringResource(R.string.user_age, user?.age ?: "error"))
                    Text(text = stringResource(R.string.user_country, user?.country ?: "error"))
                    Text(text = stringResource(R.string.user_address, user?.address ?: "error"))
                }

            }
        }


    }


}