package com.dipumba.ytsocialapp.android

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.dipumba.ytsocialapp.android.common.components.AppBar
import com.dipumba.ytsocialapp.android.destinations.HomeScreenDestination
import com.dipumba.ytsocialapp.android.destinations.LoginDestination
import com.dipumba.ytsocialapp.auth.domain.model.AuthResultData
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun SocialApp(
    authState: AuthResultData?
) {
    val navHostController = rememberNavController()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = authState){
        if (authState != null && authState.token.isBlank()){
            navHostController.navigate(LoginDestination.route){
                popUpTo(HomeScreenDestination.route){
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            AppBar(modifier = Modifier, navController = navHostController)
        }
    ) {innerPaddings ->
        DestinationsNavHost(
            modifier = Modifier.padding(innerPaddings),
            navGraph = NavGraphs.root,
            navController = navHostController
        )
    }
}