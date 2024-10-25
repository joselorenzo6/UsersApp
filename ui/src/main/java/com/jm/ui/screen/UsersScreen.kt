import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jm.domain.model.User
import com.jm.ui.model.UserState
import com.jm.ui.viewmodel.UsersViewmodel
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.jm.ui.R
import com.jm.ui.navigation.AppNavigation
import com.jm.ui.screen.LoaderComponent
import com.jm.ui.bars.ToolbarComponent
import com.jm.ui.screen.UserImage
import com.jm.ui.theme.MyAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun UserList(navController: NavHostController, viewmodel: UsersViewmodel) {
    //remember saveable
    val userState by viewmodel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val corrutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Scaffold(
        //state
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize(),
        topBar = { ToolbarComponent() }) { innerPadding ->

        val context = LocalContext.current
        LaunchedEffect(userState) {
            when (userState) {
                is UserState.Success -> {}
                is UserState.Loading -> {}
                is UserState.InternetError -> {
                    showNoInternetSnackbar(
                        corrutineScope,
                        snackbarHostState,
                        context.getString(R.string.no_internet)
                    )
                }

                is UserState.Error -> {}
            }
        }
        ShowResult(userState, viewmodel, navController, innerPadding, listState)
    }

}

private fun showNoInternetSnackbar(
    corrutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    msg: String
) {
    corrutineScope.launch {
        snackbarHostState.showSnackbar(
            message = msg,
            //actionLabel = "Click me",
            duration = SnackbarDuration.Short
        )
    }
}

@Composable
private fun ShowResult(
    userState: UserState,
    viewmodel: UsersViewmodel,
    navController: NavHostController,
    innerPadding: PaddingValues,
    listState: LazyListState
) {
    when (userState) {
        UserState.Error -> {}
        UserState.InternetError -> {}
        UserState.Loading -> {
            LoaderComponent()
        }

        is UserState.Success -> {
            val users = userState.data.orEmpty()
            ShowSuccess(users, viewmodel, navController, innerPadding, listState)
        }
    }
}

@Composable
private fun ShowSuccess(
    users: List<User>,
    viewmodel: UsersViewmodel,
    navController: NavHostController,
    innerPadding: PaddingValues,
    listState: LazyListState
) {
    if (users.isEmpty()) NoUsers() else {
        UserItems(
            viewmodel, users, navController, Modifier
                .fillMaxSize()
                .padding(innerPadding),
            listState
        )
    }
}

@Composable
private fun NoUsers() {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (btn, tv) = createRefs()
        val margin = MyAppTheme.dimens.itemSpacing
        Text("No hay resultados", Modifier.constrainAs(tv) {
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

@Composable
private fun UserItems(
    viewmodel: UsersViewmodel,
    users: List<User>,
    navController: NavHostController,
    modifier: Modifier,
    listState: LazyListState
) {

    LazyColumn(
        state = listState,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MyAppTheme.dimens.itemSpacing)
    ) {
        itemsIndexed(users) { index, user ->
            if (index == users.lastIndex) {
                LaunchedEffect(Unit) {
                    viewmodel.onUserRequested()
                }
            }

            val isOdd = index % 2 != 0 // Check if the index is odd
            UserItem(user, isOdd) {
                viewmodel.selectUser(user)
                navController.navigate(AppNavigation.NavigationItem.User.route)
            }
        }
    }

}


@Composable
private fun UserItem(user: User, isOdd: Boolean, onClicked: (User) -> Unit) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = if (isOdd)
                MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MyAppTheme.dimens.itemSpacing)
            .clickable { onClicked(user) }) {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(MyAppTheme.dimens.viewSpacing)
        ) {
            UserImage(user.img)
            Column(modifier = Modifier.padding(MyAppTheme.dimens.screenSpacing)) {
                Text(user.name)
                Text(user.gender)
                Text(user.address)
            }

        }

    }


}







