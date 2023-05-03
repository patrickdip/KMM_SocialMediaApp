package com.dipumba.ytsocialapp.android.auth.signup

import androidx.compose.runtime.Composable
import com.dipumba.ytsocialapp.android.destinations.HomeScreenDestination
import com.dipumba.ytsocialapp.android.destinations.LoginDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination(start = true)
@Composable
fun SignUp(
    navigator: DestinationsNavigator
) {
    val viewModel: SignUpViewModel = koinViewModel()
    SignUpScreen(
        uiState = viewModel.uiState,
        onUsernameChange = viewModel::updateUsername,
        onEmailChange = viewModel::updateEmail,
        onPasswordChange = viewModel::updatePassword,
        onNavigateToLogin = {
            navigator.navigate(LoginDestination)
        },
        onNavigateToHome = {
            navigator.navigate(HomeScreenDestination){
                popUpTo(0)
            }
        },
        onSignUpClick = viewModel::signUp
    )
}










