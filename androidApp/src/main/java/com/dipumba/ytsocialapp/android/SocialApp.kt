package com.dipumba.ytsocialapp.android

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.dipumba.ytsocialapp.android.auth.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun SocialApp() {
    val navHostController = rememberNavController()

    DestinationsNavHost(navGraph = NavGraphs.root, navController = navHostController)
}