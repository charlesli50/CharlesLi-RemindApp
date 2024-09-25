package com.example.charlesli_remindapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.charlesli_remindapp.ui.theme.CharlesLiRemindAppTheme

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import java.util.*

import androidx.compose.material3.Snackbar
import androidx.compose.material3.Button

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CharlesLiRemindAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RemindApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun RemindApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var reminderMessage by remember { mutableStateOf(TextFieldValue("")) }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    var showSnackbar by remember { mutableStateOf(false) }
    var reminderSet by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            selectedTime = "$hourOfDay:$minute"
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = reminderMessage,
            onValueChange = { reminderMessage = it
                            showSnackbar = false},
            label = { Text("Enter reminder") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            datePickerDialog.show()
        }) {
            Text(text = "Select Date")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            timePickerDialog.show()
        }) {
            Text(text = "Select Time")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (reminderMessage.text.isNotEmpty() && selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                snackbarMessage = "Reminder set!"
            } else {
                snackbarMessage = "Please Finish Filling the fields!"
            }
            showSnackbar = true
            reminderSet = true
        }) {
            Text(text = "Set Reminder")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            showSnackbar = false
            reminderMessage = TextFieldValue("")
            selectedDate = ""
            selectedTime = ""
            reminderSet = false
            snackbarMessage = "Reminder Cleared!"
            showSnackbar = true
        }) {
            Text(text = "Clear Reminder")
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (reminderMessage.text.isNotEmpty() && selectedDate.isNotEmpty() && selectedTime.isNotEmpty() && reminderSet) {
            Text(text = "Reminder: ${reminderMessage.text}")
            Text(text = "Date: $selectedDate")
            Text(text = "Time: $selectedTime")
        }
    }
    if (showSnackbar) {
        Snackbar(
            action = {
                Button(onClick = { showSnackbar = false }) {
                    Text("OK")
                }
            }
        ) {
            Text(snackbarMessage)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CharlesLiRemindAppTheme {
        RemindApp()
    }
}