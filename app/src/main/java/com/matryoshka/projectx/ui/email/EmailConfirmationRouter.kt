package com.matryoshka.projectx.ui.email

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun EmailConfirmationRouter(
    navController: NavController,
    email: String,
    userName: String? = null,
    viewModel: EmailConfirmationViewModel = hiltViewModel()
) {
    EmailConfirmationScreen(
        state = viewModel.state,
        email = email,
        onBackClicked = navController::popBackStack,
        onSendAgainClicked = { viewModel.onSendAgainClicked(email) },
        onCodeChanged = {
            viewModel.onCodeChanged(email, userName, navController)
        }
    )
}