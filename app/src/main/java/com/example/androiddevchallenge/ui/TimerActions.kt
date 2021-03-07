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
fun TimerActions(maxTime: MutableState<Int>, timeLeft: MutableState<Int>) {
  Row(
    modifier = Modifier.fillMaxSize(),
    horizontalArrangement = Arrangement.SpaceAround
  ) {
    Button(onClick = { startTimer(maxTime, timeLeft) }) { Text(text = "Start") }
    Button(onClick = { timer.cancel() }) { Text(text = "Stop") }
    Button(onClick = { timer.cancel(); timeLeft.value = 0 }) { Text(text = "cancel") }
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
