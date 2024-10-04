import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jm.domain.model.User
import com.jm.ui.model.UserState
import com.jm.ui.viewmodel.UsersViewmodel
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.jm.ui.navigation.AppNavigation
import com.jm.ui.screen.LoaderComponent
import com.jm.ui.screen.ToolbarComponent
import com.jm.ui.screen.UserImage
import com.jm.ui.theme.MyAppTheme

@Composable
fun UserList(navController: NavHostController, viewmodel: UsersViewmodel) {
    val users = remember { mutableStateListOf<User>() }
    val userState by viewmodel.users.collectAsStateWithLifecycle()
    var showLoader by remember { mutableStateOf(true) }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ToolbarComponent() }) { innerPadding ->

        LaunchedEffect(userState) {
            when (userState) {
                is UserState.Success -> {
                    val result = userState as UserState.Success
                    val newUsers = result.data ?: emptyList()
                    val uniqueUsers = newUsers.filterNot { newUser ->
                        users.any { it.email == newUser.email }
                    }
                    users.addAll(uniqueUsers)
                    showLoader = false
                }

                is UserState.Loading -> {
                    showLoader = true
                }

                else -> {
                    showLoader = false
                }
            }
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(4.dp)
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
        if (showLoader) LoaderComponent()
    }

}


@Composable
fun UserItem(user: User, isOdd: Boolean, onClicked: (User) -> Unit) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
         colors = CardDefaults.cardColors(
            containerColor = if (isOdd)
                MaterialTheme.colorScheme.secondaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
        ,
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







