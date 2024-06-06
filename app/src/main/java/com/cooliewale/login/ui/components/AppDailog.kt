package com.cooliewale.login.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.cooliewale.login.R

@Composable
fun LoaderDialog() {
    Dialog(onDismissRequest = {}) {
        Surface(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.size(128.dp)
        ) {
            LottieAnimation(
                resId = R.raw.loading,
                modifier = Modifier
                    .padding(16.dp)
                    .size(100.dp)
            )
        }
    }
}

@Composable
fun FailureDialog(failureMessage: String, onDialogDismiss: () -> Unit = {}) {
    Dialog(onDismissRequest = onDialogDismiss) {
        Surface(shape = RoundedCornerShape(5.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                LottieAnimation(
                    resId = R.raw.failure,
                    modifier = Modifier
                        .padding(16.dp)
                        .size(84.dp)
                )
                Text(
                    text = failureMessage,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
                FullWidthButton(
                    text = "OK",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(16.dp),
                    onClick = onDialogDismiss
                )
            }
        }
    }
}