import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jm.domain.model.User
import com.jm.ui.model.UserState
import com.jm.ui.viewmodel.UsersViewmodel
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.jm.ui.R
import com.jm.ui.navigation.AppNavigation
import com.jm.ui.screen.ToolbarComponent

@Composable
fun UserList(navController: NavHostController, viewmodel: UsersViewmodel) {
    val users = remember { mutableStateListOf<User>() }
    val userState by viewmodel.users.observeAsState()
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
                }
                is UserState.Loading ->{
                    //show loader
                }
                else -> {
                    //show error msg
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
                    viewmodel.onUserRequested()
                }

                val isOdd = index % 2 != 0 // Check if the index is odd

                // Apply different background colors for odd and even items
                val backgroundColor = if (isOdd) Color.LightGray else Color.White
                UserItem(user, backgroundColor) {
                    viewmodel.selectUser(user)
                    navController.navigate(AppNavigation.NavigationItem.User.route)}
            }
        }
    }

}

@Composable

fun UserItem(user: User, color: Color, onClicked: (User) -> Unit) {
    Card(
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .clickable { onClicked(user) }) {
        Row(
            modifier = Modifier
                .background(color)
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            UserImage(user.img)
            Column(modifier = Modifier.padding(8.dp)) {
                Text(user.name)
                Text(user.gender)
                Text(user.address)
            }

        }

    }


}

@Composable
fun UserImage(userImageUrl: String, modifier: Modifier = Modifier.size(100.dp)) {
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





