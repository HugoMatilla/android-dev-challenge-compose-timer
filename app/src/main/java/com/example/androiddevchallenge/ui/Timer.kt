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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.clockSize

@Composable
fun Timer(timeLeft: MutableState<Int>) {
    Box(
        modifier = Modifier.height(clockSize.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = timeLeft.value.format(),
            style = MaterialTheme.typography.h1,
            modifier = Modifier.fillMaxWidth(),
            textAlign = Center,
        )
//    Column(
//      modifier = Modifier.height(320.dp),
//      verticalArrangement = Arrangement.Bottom
//    ) {
// //      MillisProgressBar(timeLeft)
//    }
    }
}

@Composable
fun MillisProgressBar(timeLeft: MutableState<Int>) {
    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
        progress = ((timeLeft.value.toFloat() % 1000) / 1000F)
    )
}

// private fun Int.format() = "${this / 1000}:${this % 1000}".also { println("🚛 $this") }
private fun Int.format() = "${this / 1000}".also { println("🚛 $this") }

@Preview("TimerPreview Theme", widthDp = 360, heightDp = 640)
@Composable
fun TimerPreview() {
    Timer(mutableStateOf(10))
}
