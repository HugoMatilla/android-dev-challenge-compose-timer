package com.example.androiddevchallenge.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation.Vertical
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TimePicker(maxTime: MutableState<Int>, timeLeft: MutableState<Int>) {
  var input by remember { mutableStateOf(0f) }
  Box(
    Modifier
      .size(150.dp)
      .scrollable(
        orientation = Vertical,
        state = rememberScrollableState { delta ->
          input = getInputFromDelta(delta, input)
          maxTime.value = input.getNumberFromOffset()
          timeLeft.value = input.getNumberFromOffset() * 1000
          delta
        }
      )
      .background(Color.LightGray),
    contentAlignment = Alignment.Center
  ) {
    Text(
      maxTime.value.toString(),
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