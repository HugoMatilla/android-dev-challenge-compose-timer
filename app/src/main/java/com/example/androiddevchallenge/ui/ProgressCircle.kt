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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.clockSize

@Composable
fun ProgressCircle(maxTime: MutableState<Int>, timeLeft: MutableState<Int>) {
    val surfaceColor = MaterialTheme.colors.background
    val tickColor = MaterialTheme.colors.primary

    Canvas(
        modifier = Modifier.size(clockSize.dp)
    ) {
        val canvasWidth = this.size.width
        val canvasHeight = this.size.height
        drawArc(
            brush = SolidColor(tickColor),
            startAngle = getAngleFromMillis(maxTime.value, timeLeft.value),
            sweepAngle = 2f,
            useCenter = true,
            topLeft = Offset(x = 0f, y = 0f),
        )
        drawCircle(
            color = surfaceColor,
            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
            radius = clockSize * 1.5f,
        )
    }
}

fun getAngleFromMillis(maxTime: Int, millis: Int): Float {
    if (maxTime == 0) return 270f
    if (millis == 0) return 270f
    if (millis == maxTime * 1000) return 270f
    val time = (millis - 1000) / (maxTime * 1000f)
    val angle = time * 360f
    return angle - 90
}
