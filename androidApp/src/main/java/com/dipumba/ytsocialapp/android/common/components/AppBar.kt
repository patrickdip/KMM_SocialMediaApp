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
import com.dipumba.ytsocialapp.android.destinations.HomeScreenDestination
import com.dipumba.ytsocialapp.android.destinations.LoginDestination
import com.dipumba.ytsocialapp.android.destinations.SignUpDestination
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@Composable
fun AppBar(
    modifier: Modifier,
    navController: NavHostController
) {
    val currentDestination = navController.currentDestinationAsState().value

    Surface(
        modifier = modifier,
        elevation = SmallElevation
    ) {
        TopAppBar(
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.surface,
            title = {
                Text(
                    text = stringResource(id = getAppBarTitle(currentDestination?.route)),
                    style = MaterialTheme.typography.h6
                )
            },
            navigationIcon = if (shouldShowNavIcon(currentDestination?.route)) {
                {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.round_arrow_back_24),
                            contentDescription = null,
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }
            } else null,
            actions = {
                AnimatedVisibility(visible = currentDestination?.route == HomeScreenDestination.route) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.person_circle_icon),
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}

private fun shouldShowNavIcon(destinationRoute: String?): Boolean {
    return when (destinationRoute) {
        LoginDestination.route -> false
        else -> false
    }
}

private fun getAppBarTitle(destinationRoute: String?): Int {
    return when (destinationRoute) {
        LoginDestination.route -> R.string.login_destination_title
        SignUpDestination.route -> R.string.signup_destination_title
        HomeScreenDestination.route -> R.string.app_name
        else -> R.string.no_destination_title
    }
}