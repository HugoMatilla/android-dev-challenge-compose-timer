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
package com.example.androiddevchallenge.ui

import android.os.CountDownTimer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

lateinit var timer: CountDownTimer

@Composable
fun TimerActions(maxTime: MutableState<Int>, timeLeft: MutableState<Int>, running: MutableState<Boolean>) {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Button(
            enabled = !running.value,
            onClick = { startTimer(maxTime, timeLeft); running.value = true }
        ) { Text(text = "Start") }
        Button(
            enabled = running.value && timeLeft.value != 0,
            onClick = {
                timer.cancel(); maxTime.value = timeLeft.value / 1000; running.value = false
            }
        ) { Text(text = "Pause") }
        Button(
            onClick = { timer.cancel(); timeLeft.value = 0; running.value = false }
        ) { Text(text = "Reset") }
    }
}

fun startTimer(maxTime: MutableState<Int>, timeLeft: MutableState<Int>) {
    timer = object : CountDownTimer((maxTime.value) * 1_000L, 1) {
        override fun onTick(millisUntilFinished: Long) {
            timeLeft.value = ((millisUntilFinished) + 1000).toInt()
        }

        override fun onFinish() {
            timeLeft.value = 0
        }
    }
    timer.start()
}
