package com.yeshuwahane.d4c.presenatation.tickets

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import java.io.File



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketScreen(
    onBackPressed: () -> Unit
) {
    val viewModel = hiltViewModel<TicketViewModel>()
    val context = LocalContext.current
    val ticketState by viewModel.ticketState.collectAsState()

    var ticketType by remember { mutableStateOf("67ab787870baa5efe5404d63") } // pre-filled
    var message by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // Convert URI to File
    fun uriToFile(uri: Uri): File {
        return uri.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val file = File(context.cacheDir, "selected_image.jpg")
            inputStream?.use { input ->
                file.outputStream().use { output -> input.copyTo(output) }
            }
            file
        }
    }

    // Image Picker
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Ticket") },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = ticketType,
                onValueChange = { ticketType = it },
                label = { Text("Ticket Type") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Message") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                maxLines = 5
            )

            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text("Select Image")
            }

            imageUri?.let {
                Text(
                    text = "Image Selected: ${it.lastPathSegment}",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Button(
                onClick = {
                    val file = imageUri?.let { uriToFile(it) }
                    viewModel.submitTicket(ticketType, message, file)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Submit")
            }

            when {
                ticketState.isLoading() -> CircularProgressIndicator()
                ticketState.isSuccess() -> Text("Ticket submitted!", color = Color.Green)
                ticketState.isError() -> Text(ticketState.error?.message ?: "Error", color = Color.Red)
            }
        }
    }
}
