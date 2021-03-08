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
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.TimePicker
import com.example.androiddevchallenge.ui.Timer
import com.example.androiddevchallenge.ui.TimerActions
import com.example.androiddevchallenge.ui.theme.MyTheme

data class TimeLeft(val minutes: Int, val millis: Int)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val isDarkTheme = remember { mutableStateOf(true) }
    val maxTime = remember { mutableStateOf(0) }
    val timeLeft = remember { mutableStateOf(0) }
    val running = remember { mutableStateOf(false) }
    MyTheme(darkTheme = isDarkTheme.value) {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        isDarkTheme.value = !isDarkTheme.value
                    }
                ) { Text(text = if (isDarkTheme.value) "go Light" else "go Dark") }
                TimerWrapper(maxTime, timeLeft, running)
//      MillisProgressBar(timeLeft)
                TimerActions(maxTime, timeLeft, running)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TimerWrapper(maxTime: MutableState<Int>, timeLeft: MutableState<Int>, running: MutableState<Boolean>) {
    Surface(
        color = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(32.dp)
    ) {
        Box {
            AnimatedVisibility(visible = !running.value, enter = fadeIn(), exit = fadeOut()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
//          TimePicker(mutableStateOf(1), mutableStateOf(10))
                    TimePicker(maxTime, timeLeft)
                }
            }
            AnimatedVisibility(visible = running.value, enter = fadeIn(), exit = fadeOut()) {
                Timer(timeLeft)
            }
        }
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
