package com.example.androiddevchallenge.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Composable
fun Icon(iconId: Int) {
  Image(
    painter = painterResource(iconId),
    contentDescription = null,
  )
}