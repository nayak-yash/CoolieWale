package com.cooliewale.login.ui.screens

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cooliewale.login.ui.components.FailureDialog
import com.cooliewale.login.ui.components.FullWidthButton
import com.cooliewale.login.ui.components.LoaderDialog
import com.cooliewale.login.ui.components.OtpView
import com.cooliewale.login.ui.theme.Vermillion
import com.cooliewale.login.viewmodel.OTPViewModel

@Composable
fun OtpScreen(
    viewModel: OTPViewModel = hiltViewModel(),
    onOtpTextChange: (String) -> Unit = viewModel::setOtp,
    onVerifyClick: () -> Unit = viewModel::signInWithCredential,
    onDialogDismiss: () -> Unit = viewModel::clearError,
    onSuccess: () -> Unit
) {
    val state = viewModel.otpState

    val context = LocalContext.current

    if (state.isLoading) {
        LoaderDialog()
    }

    if (state.error != null) {
        FailureDialog(state.error, onDialogDismiss = onDialogDismiss)
    }

    if (state.success != null) {
        Toast.makeText(context, state.success, Toast.LENGTH_SHORT).show()
        viewModel.setSuccess(null)
        onSuccess()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Mobile Phone Verification",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 120.dp, bottom = 8.dp)
        )
        Text(
            text = "Enter the 6-digit verification code that was sent to your phone number",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        OtpView(otpText = state.otp, otpCount = 6, onOtpTextChange = onOtpTextChange, modifier = Modifier.fillMaxWidth())
        FullWidthButton(
            text = "Verify Account",
            onClick = onVerifyClick,
            modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)
        )
        ResendLink(onResendClick = {
            viewModel.createUserWithPhone(context as Activity)
        })
    }
}

@Composable
fun ResendLink(onResendClick: () -> Unit) {
    Row (
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.padding(vertical = 16.dp)
    ){
        Text(
            text = "Didn't receive code?",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            )
        )
        Text(
            text = " Resend",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W500
            ),
            color = Vermillion,
            modifier = Modifier.clickable { onResendClick }
        )
    }
}
