package com.itssagnikmukherjee.dicegamemultiplayer

import android.annotation.SuppressLint
import android.provider.MediaStore.Images
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.random.Random

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    var player1Score by remember { mutableIntStateOf(0) }
    var player2Score by remember { mutableIntStateOf(0) }
    var player1turn by remember { mutableStateOf(true) }
    var currentImg by remember { mutableIntStateOf(1) }

    val rotation = remember { Animatable(0f) }
    val coroutineScope = rememberCoroutineScope()

    suspend fun rollDice() {
        for (i in 1..10) {
            rotation.animateTo(
                targetValue = rotation.value + 360f,
                animationSpec = tween(durationMillis = 100)
            )
        }
        currentImg = Random.nextInt(6) + 1
    }

    if (player1Score>=20 || player2Score>=20) {
        if (player1Score>player2Score) {
            Text(text = "Player 1 wins")
        } else {
            Text(text = "Player 2 wins")
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Player 1 score : $player1Score")
            Text(text = "Player 2 score : $player2Score")
        }
        Text(text = if (player1turn) "Player 1 turn" else "Player 2 turn")

        // Rotate the image
        Image(
            painter = painterResource(getImageList()[currentImg - 1]),
            contentDescription = "",
            modifier = Modifier
                .rotate(rotation.value)
                .size(100.dp)
        )

        Row {
            Button(
                onClick = {
                    coroutineScope.launch {
                        rollDice()
                        player1Score += currentImg
                        player1turn = false
                    }
                },
                enabled = player1turn
            ) {
                Text(text = "Player 1")
            }
            Button(
                onClick = {
                    coroutineScope.launch {
                        rollDice()
                        player2Score += currentImg
                        player1turn = true
                    }
                },
                enabled = !player1turn
            ) {
                Text(text = "Player 2")
            }
        }
    }
}

@Composable
fun getImageList(): List<Int> {
    return listOf(
        R.drawable.dice_1,
        R.drawable.dice_2,
        R.drawable.dice_3,
        R.drawable.dice_4,
        R.drawable.dice_5,
        R.drawable.dice_6,
    )
}
