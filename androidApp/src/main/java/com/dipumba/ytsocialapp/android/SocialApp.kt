package com.dipumba.ytsocialapp.android

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dipumba.ytsocialapp.android.common.components.AppBar
import com.dipumba.ytsocialapp.android.destinations.HomeDestination
import com.dipumba.ytsocialapp.android.destinations.LoginDestination
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.utils.currentDestinationAsState

@Composable
fun SocialApp(
    uiState: MainActivityUiState
) {
    val navHostController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val systemUiController = rememberSystemUiController()

    val isSystemInDark = isSystemInDarkTheme()
    val statusBarColor = if (isSystemInDark){
        MaterialTheme.colors.surface
    }else{
        MaterialTheme.colors.surface.copy(alpha = 0.95f)
    }
    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
            darkIcons = !isSystemInDark
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(modifier = Modifier, navHostController = navHostController)
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = navHostController.currentDestinationAsState().value == HomeDestination
            ){
                FloatingActionButton(
                    onClick = {
                        navHostController.navigate(
                            route = "create_post"
                        )
                    },
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    ) {innerPaddings ->
        DestinationsNavHost(
            modifier = Modifier.padding(innerPaddings),
            navGraph = NavGraphs.root,
            navController = navHostController
        )
    }

    when(uiState){
        MainActivityUiState.Loading -> {}
        is MainActivityUiState.Success -> {
            LaunchedEffect(key1 = Unit) {
                if (uiState.currentUser.token.isEmpty()) {
                    navHostController.navigate(LoginDestination.route) {
                        popUpTo(HomeDestination.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}