package com.yeshuwahane.d4c.presenatation.auth

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Snackbar
import androidx.compose.material.TextButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel





@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(
    onNavigation: () -> Unit
) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val phoneNumber = viewModel.phoneNumber
    val otp = viewModel.otp
    val showOtp = viewModel.showOtpField
    val isLoading = viewModel.isLoading
    val isOtpLoading = viewModel.isOtpLoading
    val errorMessage = viewModel.errorMessage
    val navigateToProduct = viewModel.navigateToProductScreen

    val snackbarHostState = remember { SnackbarHostState() }

    // show error snackbar when errorMessage is set
    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    LaunchedEffect(navigateToProduct) {
        if (navigateToProduct) {
            onNavigation.invoke()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .imePadding() // handles keyboard visibility
                    .padding(bottom = 16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.Bottom)
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = phoneNumber,
                    onValueChange = { viewModel.phoneNumber = it },
                    label = { Text("Phone Number") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    enabled = !showOtp,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.sendOtp() },
                    enabled = !isLoading && !showOtp
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    } else {
                        Text(if (showOtp) "OTP Sent Successfully" else "Send OTP")
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .alpha(if (showOtp) 1f else 0f)
                        .pointerInput(showOtp) {}
                ) {
                    OtpInput(
                        otpText = otp,
                        onOtpTextChange = { viewModel.otp = it },
                        isEnabled = showOtp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.verifyOtp() },
                        enabled = otp.length == 6 && showOtp && !isOtpLoading
                    ) {
                        if (isOtpLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Verify OTP")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun OtpInput(
    otpText: String,
    onOtpTextChange: (String) -> Unit,
    isEnabled: Boolean
) {
    val focusRequesters = List(6) { FocusRequester() }
    val interactionSources = List(6) { remember { MutableInteractionSource() } }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        (0 until 6).forEach { index ->
            val char = otpText.getOrNull(index)?.toString() ?: ""
            OutlinedTextField(
                value = char,
                onValueChange = { value ->
                    if (!isEnabled) return@OutlinedTextField
                    if (value.length <= 1 && (value.isEmpty() || value.first().isDigit())) {
                        val newOtp = StringBuilder(otpText).apply {
                            if (length > index) {
                                if (value.isEmpty()) deleteCharAt(index)
                                else setCharAt(index, value.first())
                            } else if (value.isNotEmpty()) {
                                append(value)
                            }
                        }.toString().take(6)

                        onOtpTextChange(newOtp)

                        if (value.isNotEmpty() && index < 5) {
                            focusRequesters[index + 1].requestFocus()
                        }
                    }
                },
                modifier = Modifier
                    .width(41.dp)
                    .height(46.dp)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent {
                        if (!isEnabled) return@onKeyEvent false
                        if (it.key == Key.Backspace && char.isEmpty() && index > 0) {
                            focusRequesters[index - 1].requestFocus()
                            val newOtp = StringBuilder(otpText).apply {
                                if (length > index - 1) deleteCharAt(index - 1)
                            }.toString()
                            onOtpTextChange(newOtp)
                            true
                        } else false
                    },
                readOnly = false,
                enabled = isEnabled,
                interactionSource = interactionSources[index],
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 11.sp
                )
            )
        }
    }

    LaunchedEffect(isEnabled) {
        if (isEnabled) {
            focusRequesters.first().requestFocus()
        }
    }
}
