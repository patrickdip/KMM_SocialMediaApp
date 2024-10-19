package com.dipumba.ytsocialapp.android.common.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.dipumba.ytsocialapp.android.R
import com.dipumba.ytsocialapp.android.common.theming.SmallElevation
import com.dipumba.ytsocialapp.android.destinations.CreatePostDestination
import com.dipumba.ytsocialapp.android.destinations.EditProfileDestination
import com.dipumba.ytsocialapp.android.destinations.FollowersDestination
import com.dipumba.ytsocialapp.android.destinations.FollowingDestination
import com.dipumba.ytsocialapp.android.destinations.HomeDestination
import com.dipumba.ytsocialapp.android.destinations.LoginDestination
import com.dipumba.ytsocialapp.android.destinations.PostDetailDestination
import com.dipumba.ytsocialapp.android.destinations.ProfileDestination
import com.dipumba.ytsocialapp.android.destinations.SignUpDestination
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@Composable
fun AppBar(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val currentDestination = navHostController.currentDestinationAsState().value

    Surface(
        modifier = modifier,
        elevation = SmallElevation
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = getAppBarTitle(currentDestination?.route)))
            },
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.surface,
            actions = {
                AnimatedVisibility(visible = currentDestination?.route == HomeDestination.route) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.person_circle_icon),
                            contentDescription = null
                        )
                    }
                }
            },
            navigationIcon = if (shouldShowNavigationIcon(currentDestination?.route)) {
                {
                    IconButton(onClick = { navHostController.navigateUp() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_arrow_back),
                            contentDescription = null
                        )
                    }
                }
            } else {
                null
            }
        )
    }
}

private fun getAppBarTitle(currentDestinationRoute: String?): Int {
    return when (currentDestinationRoute) {
        LoginDestination.route -> R.string.login_destination_title
        SignUpDestination.route -> R.string.signup_destination_title
        HomeDestination.route -> R.string.home_destination_title
        PostDetailDestination.route -> R.string.post_detail_destination_title
        ProfileDestination.route -> R.string.profile_destination_title
        EditProfileDestination.route -> R.string.edit_profile_destination_title
        FollowingDestination.route -> R.string.following_text
        FollowersDestination.route -> R.string.followers_text
        CreatePostDestination.route -> R.string.create_post_destination_title
        else -> R.string.no_destination_title
    }
}

private fun shouldShowNavigationIcon(currentDestinationRoute: String?): Boolean {
    return !(currentDestinationRoute == LoginDestination.route
            || currentDestinationRoute == SignUpDestination.route
            || currentDestinationRoute == HomeDestination.route
            || currentDestinationRoute == null
            )
}















