/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme

lateinit var input: MutableState<TextFieldValue>
lateinit var timer: CountDownTimer
lateinit var counter: MutableState<Int>

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MyTheme {
        MyApp()
      }
    }
  }
}

@Composable
fun MyApp() {
  Surface(color = MaterialTheme.colors.background) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.SpaceAround
    ) {
      Clock()
      Buttons()
    }
  }
}

@Composable
fun Buttons() {
  Row(
    modifier = Modifier.fillMaxSize(),
    horizontalArrangement = Arrangement.SpaceAround
  ) {
    Button(onClick = { startTimer() }) { Text(text = "Start") }
    Button(onClick = { stopTimer() }) { Text(text = "Stop") }
    Button(onClick = { stopTimer(); counter.value = 0 }) { Text(text = "cancel") }
  }
}

@Composable
fun Clock() {
  counter = remember { mutableStateOf(0) }
  Column(
    modifier = Modifier.height(120.dp),
    verticalArrangement = Arrangement.SpaceAround
  ) {
    Input()
    Text(text = "Time ${counter.value}", style = MaterialTheme.typography.h3)
  }
}

@Composable
fun Input() {
  input = remember { mutableStateOf(TextFieldValue()) }
  TextField(
    value = input.value,
    onValueChange = { new ->
      input.value = if (new.text.isBlank()) TextFieldValue(text = "0") else new
      counter.value = input.value.text.toInt()
    },
    singleLine = true,
    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
    modifier = Modifier.fillMaxWidth()
  )
}

fun stopTimer() {
  timer.cancel()
}

fun startTimer() {
  updateTimer(if (input.value.text.isBlank()) 0 else input.value.text.toInt())
  timer.start()
}

fun updateTimer(newCounterValue: Int) {
  counter.value = newCounterValue + 1
  timer = object : CountDownTimer(newCounterValue * 1_000L, 1_000) {
    override fun onTick(millisUntilFinished: Long) {
      counter.value = counter.value - 1
    }

    override fun onFinish() {
      counter.value = newCounterValue
    }
  }
}

// ----- ----- ----- ----- --------------- //
private class Time(hours: State<Int>, minutes: State<Int>, seconds: State<Int>, millis: State<Int>) {
  val hours by hours
  val minutes by minutes
  val seconds by seconds
  val millis by millis
}

@Composable
private fun FancyClock(time: Time) {
  Row {
    Text(text = "${time.hours}:${time.minutes}:${time.seconds}")
//    Text(text = "${time.hours}:${time.minutes}:${time.seconds}:${time.millis}:")
  }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
  MyTheme {
    MyApp()
  }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
  MyTheme(darkTheme = true) {
    MyApp()
  }
}
