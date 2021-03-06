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
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme

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
  val output = remember { mutableStateOf(0) }
  Surface(color = MaterialTheme.colors.background) {
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.SpaceAround
    ) {
      InputTimer(output)
      Clock(output)
      Buttons(output)
    }
  }
}

@Composable
fun InputTimer(output: MutableState<Int>) {
  var input by remember { mutableStateOf(0f) }
  Box(
    Modifier
      .size(150.dp)
      .scrollable(
        orientation = Orientation.Vertical,
        state = rememberScrollableState { delta ->
          input = getInputFromDelta(delta, input)
          output.value = input.getNumberFromOffset()
          delta
        }
      )
      .background(Color.LightGray),
    contentAlignment = Alignment.Center
  ) {
    Text(
      output.value.toString(),
      style = MaterialTheme.typography.h3
    )
  }
}

private fun Float.getNumberFromOffset() = (if (this < 0) 60 + this else this).toInt()

fun getInputFromDelta(delta: Float, number: Float): Float {
  var result = number
  when {
    result > number + delta -> result = (number + 0.25f) % 60
    result < number + delta -> result = (number - 0.25f) % 60
  }
  return result
}

@Composable
fun Buttons(output: MutableState<Int>) {
  Row(
    modifier = Modifier.fillMaxSize(),
    horizontalArrangement = Arrangement.SpaceAround
  ) {
    Button(onClick = { startTimer(output) }) { Text(text = "Start") }
    Button(onClick = { stopTimer() }) { Text(text = "Stop") }
    Button(onClick = { stopTimer(); counter.value = 0 }) { Text(text = "cancel") }
  }
}

@Composable
fun Clock(output: MutableState<Int>) {
  counter = remember { mutableStateOf(0) }
  Column(
    modifier = Modifier.height(320.dp),
    verticalArrangement = Arrangement.SpaceEvenly
  ) {
    Text(
      text = "Time ${counter.value}", style = MaterialTheme.typography.h3,
      modifier = Modifier.fillMaxWidth(),
      textAlign = Center
    )
    if (output.value > 0)
      LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
        progress = (counter.value.toFloat() / output.value.toFloat())
      )
  }
}
//
//@Composable
//fun Input() {
//  input = remember { mutableStateOf(TextFieldValue()) }
//  TextField(
//    value = input.value,
//    onValueChange = { new ->
//      input.value = new
//      counter.value = if (new.text.isBlank()) 1 else new.text.toInt()
////      output.value = if (new.text.isBlank()) 1 else new.text.toInt()
//    },
//    singleLine = true,
//    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//    modifier = Modifier.fillMaxWidth()
//  )
//}

fun stopTimer() {
  timer.cancel()
}

fun startTimer(output: MutableState<Int>) {
  updateTimer(output)
  timer.start()
}

fun updateTimer(output: MutableState<Int>) {
  counter.value = output.value + 1
  timer = object : CountDownTimer(output.value * 1_000L, 1_000) {
    override fun onTick(millisUntilFinished: Long) {
      counter.value = counter.value - 1
    }

    override fun onFinish() {
      counter.value = output.value
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
