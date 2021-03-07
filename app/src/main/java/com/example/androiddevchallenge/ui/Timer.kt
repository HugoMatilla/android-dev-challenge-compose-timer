package com.example.androiddevchallenge.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign.Center
import androidx.compose.ui.unit.dp

@Composable
fun Timer(maxTime: MutableState<Int>, timeLeft: MutableState<Int>) {
  Column(
    modifier = Modifier.height(320.dp),
    verticalArrangement = Arrangement.SpaceEvenly
  ) {
    Text(
      text = "${timeLeft.value.format()}", style = MaterialTheme.typography.h3,
      modifier = Modifier.fillMaxWidth(),
      textAlign = Center,
      color = Color.Blue
    )
    if (maxTime.value > 0)
      LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth(),
//        progress = (timeLeft.value.toFloat() / maxTime.value.toFloat())
        progress = ((timeLeft.value.toFloat() % 1000) / 1000F)
      )
  }
}

//private fun Int.format() = "${this / 1000}:${this % 1000}".also { println("🚛 $this") }
private fun Int.format() = "${this / 1000}".also { println("🚛 $this") }

