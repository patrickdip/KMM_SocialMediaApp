package com.dipumba.ytsocialapp.android.account.edit

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
@Destination
fun EditProfile(
    navigator: DestinationsNavigator,
    userId: Int
) {
    val viewModel: EditProfileViewModel = koinViewModel()

    EditProfileScreen(
        editProfileUiState = viewModel.uiState,
        onNameChange = viewModel::onNameChange,
        bioTextFieldValue = viewModel.bioTextFieldValue,
        onBioChange = viewModel::onBioChange,
        onUploadButtonClick = { viewModel.uploadProfile() },
        onUploadSucceed = { navigator.navigateUp() },
        fetchProfile = { viewModel.fetchProfile(userId)}
    )
}






