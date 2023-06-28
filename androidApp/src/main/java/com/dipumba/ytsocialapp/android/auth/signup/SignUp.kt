package com.dipumba.ytsocialapp.android.auth.signup

import androidx.compose.runtime.Composable
import com.dipumba.ytsocialapp.android.destinations.HomeDestination
import com.dipumba.ytsocialapp.android.destinations.LoginDestination
import com.dipumba.ytsocialapp.android.destinations.SignUpDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Destination
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
            navigator.navigate(LoginDestination){
                popUpTo(SignUpDestination.route){
                    inclusive = true
                }
            }
        },
        onNavigateToHome = {
            navigator.navigate(HomeDestination){
                popUpTo(SignUpDestination.route){inclusive = true}
            }
        },
        onSignUpClick = viewModel::signUp
    )
}










