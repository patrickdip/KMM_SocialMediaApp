package com.dipumba.ytsocialapp.android.account.edit

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.dipumba.ytsocialapp.android.R
import com.dipumba.ytsocialapp.android.common.components.CircleImage
import com.dipumba.ytsocialapp.android.common.components.CustomTextField
import com.dipumba.ytsocialapp.android.common.components.ScreenLevelLoadingErrorView
import com.dipumba.ytsocialapp.android.common.components.ScreenLevelLoadingView
import com.dipumba.ytsocialapp.android.common.theming.ButtonHeight
import com.dipumba.ytsocialapp.android.common.theming.ExtraLargeSpacing
import com.dipumba.ytsocialapp.android.common.theming.Gray
import com.dipumba.ytsocialapp.android.common.theming.LargeSpacing
import com.dipumba.ytsocialapp.android.common.theming.SmallElevation
import com.dipumba.ytsocialapp.android.common.util.toCurrentUrl

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    editProfileUiState: EditProfileUiState,
    onNameChange: (String) -> Unit,
    bioTextFieldValue: TextFieldValue,
    onBioChange: (TextFieldValue) -> Unit,
    onUploadSucceed: () -> Unit,
    userId: Long,
    onUiAction: (EditProfileUiAction) -> Unit
) {

    val context = LocalContext.current
    var selectedImage by remember { mutableStateOf<Uri?>(null) }
    val pickImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImage = uri }
    )

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        if (editProfileUiState.profile != null){
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(
                        color = if (isSystemInDarkTheme()) {
                            MaterialTheme.colors.background
                        } else {
                            MaterialTheme.colors.surface
                        }
                    )
                    .padding(ExtraLargeSpacing),
                verticalArrangement = Arrangement.spacedBy(LargeSpacing),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box {
                    CircleImage(
                        modifier = modifier.size(120.dp),
                        url = editProfileUiState.profile.imageUrl?.toCurrentUrl(),
                        uri = selectedImage,
                        onClick = {}
                    )

                    IconButton(
                        onClick = {
                            pickImage.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        },
                        modifier = modifier
                            .align(Alignment.BottomEnd)
                            .shadow(
                                elevation = SmallElevation,
                                shape = RoundedCornerShape(percent = 25)
                            )
                            .background(
                                color = MaterialTheme.colors.surface,
                                shape = RoundedCornerShape(percent = 25)
                            )
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }

                Spacer(modifier = modifier.height(LargeSpacing))

                CustomTextField(
                    value = editProfileUiState.profile.name,
                    onValueChange = onNameChange,
                    hint = R.string.username_hint
                )

                BioTextField(value = bioTextFieldValue, onValueChange = onBioChange)

                Button(
                    onClick = {
                        selectedImage?.let {
                            onUiAction(EditProfileUiAction.UploadProfileAction(imageUri = it))
                        } ?: run {
                            // No image selected, proceed with no image
                            onUiAction(EditProfileUiAction.UploadProfileAction())
                        }
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(ButtonHeight),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = stringResource(id = R.string.upload_changes_text))
                }

            }
        }

        if (editProfileUiState.profile == null && editProfileUiState.errorMessage != null){
            ScreenLevelLoadingErrorView {
                onUiAction(EditProfileUiAction.FetchProfileAction(userId = userId))
            }
        }

        if (editProfileUiState.isLoading) {
            ScreenLevelLoadingView()
        }

        if (editProfileUiState.isLoading){
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        onUiAction(EditProfileUiAction.FetchProfileAction(userId = userId))
    })

    LaunchedEffect(
        key1 = editProfileUiState.uploadSucceed,
        key2 = editProfileUiState.errorMessage,
        block = {
            if (editProfileUiState.uploadSucceed){
                onUploadSucceed()
            }

            if (editProfileUiState.profile != null && editProfileUiState.errorMessage != null){
                Toast.makeText(context, editProfileUiState.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    )

}

@Composable
fun BioTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp),
        value = value,
        onValueChange = {
            onValueChange(
                TextFieldValue(
                    text = it.text,
                    selection = TextRange(it.text.length)
                )
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = if (isSystemInDarkTheme()) {
                MaterialTheme.colors.surface
            } else {
                Gray
            },
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        ),
        textStyle = MaterialTheme.typography.body2,
        placeholder = {
            Text(
                text = stringResource(id = R.string.user_bio_hint),
                style = MaterialTheme.typography.body2
            )
        },
        shape = MaterialTheme.shapes.medium
    )
}


















