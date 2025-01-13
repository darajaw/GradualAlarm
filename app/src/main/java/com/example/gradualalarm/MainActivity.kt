package com.geeksforgeeks.jctimepicker

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gradualalarm.AlarmManager
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alarmManager = AlarmManager(this)
        setContent {
            // Calling the composable function
            // to display element and its contents
            AlarmContent(alarmManager)
        }
    }
}

// Creating a composable function to display Top Bar
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("GFG | Time Picker", color = Color.Black) })},
        content = { AlarmContent() }
    )
}

// Creating a composable function
// to create a Time Picker
// Calling this function as content
// in the above function
@Composable
//default null the alarmManager so it can be previewed
fun AlarmContent(alarmManager: AlarmManager? = null){

    // Fetching local context
    val loContext = LocalContext.current

    // Declaring and initializing a calendar
    val curCalendar = Calendar.getInstance()
    val curHour = curCalendar[Calendar.HOUR_OF_DAY]
    val curMinute = curCalendar[Calendar.MINUTE]

    // Value for storing time as a string
    val setTime = remember { mutableStateOf("") }

    val setCalendar = Calendar.getInstance()

    // Creating a TimePicker dialog
    val timePickerDialog = TimePickerDialog(
        loContext,
        {_, setHour : Int, setMinute: Int ->
            setTime.value = "$setHour:$setMinute"
            setCalendar.set(Calendar.HOUR_OF_DAY, setHour)
            setCalendar.set(Calendar.MINUTE, setMinute)
            alarmManager?.scheduleAlarm(setCalendar.timeInMillis)
        }, curHour, curMinute, false
    )

    //Physical build for the app
    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment =  Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "Selected Time: ${setTime.value}", fontSize = 30.sp)

        Spacer(modifier = Modifier.height(30.dp))

        // On button click, TimePicker is
        // displayed, user can select a time
        Button(onClick = {
            timePickerDialog.show()
        }) {
            Text(text = "Set Alarm")
        }

        Spacer(modifier = Modifier.height(30.dp))

        // On button click, alarm is canceled and setTime is removed
        Button(onClick = {
            alarmManager?.cancelAlarm()
            setTime.value = ""
        }) {
            Text(text = "Cancel Alarm")
        }
    }
}

// For displaying preview in
// the Android Studio IDE emulator
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AlarmContent()
}
