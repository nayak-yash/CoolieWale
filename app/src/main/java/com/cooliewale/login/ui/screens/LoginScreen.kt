package com.cooliewale.login.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cooliewale.login.R
import com.cooliewale.login.ui.components.AppTextField
import com.cooliewale.login.ui.components.FailureDialog
import com.cooliewale.login.ui.components.FullWidthButton
import com.cooliewale.login.ui.components.LoaderDialog
import com.cooliewale.login.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onPhoneNumberChange: (String) -> Unit = viewModel::setPhoneNumber,
    onDialogDismiss: () -> Unit = viewModel::clearError,
    onSuccess: (String) -> Unit
) {
    val state = viewModel.loginState

    val context = LocalContext.current

    if (state.isLoading) {
        LoaderDialog()
    }

    if (state.error != null) {
        FailureDialog(failureMessage = state.error, onDialogDismiss = onDialogDismiss)
    }

    if (state.success != null) {
        Toast.makeText(context, state.success, Toast.LENGTH_SHORT).show()
        viewModel.setSuccess(null)
        onSuccess(state.phoneNumber)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
    ) {
        TopGreeting()
        Text(
            text = "Enter your Mobile Number",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            ),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 30.dp, bottom = 4.dp)
        )
        Text(
            text = "A verification code will be sent to your phone",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        LoginForm(
            phoneNumber = state.phoneNumber,
            isValidPhoneNumber = state.isValidPhoneNumber ?: true,
            onPhoneNumberChange = onPhoneNumberChange,
            onLoginClick = {
                viewModel.createUserWithPhone(mobile = state.phoneNumber, activity = context as Activity)
            }
        )
    }
}

@Composable
fun TopGreeting() {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            contentDescription = "App Logo",
            painter = painterResource(id = R.drawable.ic_logo),
            modifier = Modifier
                .padding(top = 120.dp)
                .requiredSize(100.dp)
                .border(width = 3.dp, color = Color.White, shape = CircleShape),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "Passenger Login",
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 30.dp)
        )
    }
}

@Composable
fun LoginForm(
    phoneNumber: String,
    isValidPhoneNumber: Boolean,
    onPhoneNumberChange: (String) -> Unit,
    onLoginClick: (String) -> Unit
) {
    AppTextField(
        value = phoneNumber,
        label = "Phone number",
        onValueChange = onPhoneNumberChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .background(MaterialTheme.colorScheme.background),
        leadingIcon = { Icon(Icons.Outlined.Phone, "Phone") },
        isError = !isValidPhoneNumber,
        helperText = stringResource(R.string.message_field_phone_invalid)
    )
    FullWidthButton(
        text = "Login",
        onClick = { onLoginClick(phoneNumber) },
        modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)
    )
}