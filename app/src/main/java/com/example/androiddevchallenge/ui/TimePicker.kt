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

import androidx.compose.foundation.gestures.Orientation.Vertical
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign.Center
import androidx.compose.ui.text.style.TextAlign.End
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.ui.theme.MyTheme

@Composable
fun TimePicker(maxTime: MutableState<Int>, timeLeft: MutableState<Int>) {
    var input by remember { mutableStateOf(0f) }
    Box(
        modifier = Modifier
            .height(320.dp)
            .scrollable(
                orientation = Vertical,
                state = rememberScrollableState { delta ->
                    input = getInputFromDelta(delta, input)
                    maxTime.value = input.getNumberFromOffset()
                    timeLeft.value = input.getNumberFromOffset() * 1000
                    delta
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            maxTime.value.toString(),
            style = MaterialTheme.typography.h1,
            textAlign = End
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

@Preview("TimerPicker", widthDp = 360, heightDp = 400)
@Composable
fun TimerPickerPreview() {
    MyTheme() {
        TimePicker(mutableStateOf(10), mutableStateOf(10))
    }
}
