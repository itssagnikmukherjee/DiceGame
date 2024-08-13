package com.itssagnikmukherjee.dicegamemultiplayer

import android.annotation.SuppressLint
import android.provider.MediaStore.Images
import android.widget.ImageButton
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            Column(
                modifier = Modifier.fillMaxSize().padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(painter = painterResource(id = R.drawable.batman), contentDescription = "",modifier = Modifier.size(100.dp))
                Spacer(modifier = Modifier.height(30.dp))
                Text(text = "Batman Wins !", fontSize = 40.sp, fontWeight = FontWeight.Bold)
                Text(text = "$player1Score", fontSize = 40.sp, fontWeight = FontWeight.Bold)
            }
        } else {
            Column (
                modifier = Modifier.fillMaxSize().padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Image(painter = painterResource(id = R.drawable.spiderman), contentDescription = "", modifier = Modifier.size(100.dp))
                Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Spiderman Wins !", fontSize = 30.sp,fontWeight = FontWeight.Bold)
                Text(text = "$player2Score", fontSize = 40.sp, fontWeight = FontWeight.Bold)
            }
        }
    }else{
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = if (player1turn) "Batman" else "Spiderman", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(30.dp))

        Image(
            painter = painterResource(getImageList()[currentImg - 1]),
            contentDescription = "",
            modifier = Modifier
                .rotate(rotation.value)
                .size(300.dp)
        )
            Row(
                modifier = Modifier.fillMaxWidth(0.6f),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Button(onClick = {
                        coroutineScope.launch {
                            rollDice()
                            player1Score += currentImg
                            player1turn = false
                        } }, modifier = Modifier.size(100.dp),enabled = player1turn,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFe67e22)
                        )

                    ) {
                        Image(painter = painterResource(id = R.drawable.batman), contentDescription = "")
                    }

                    Text(text = "$player1Score", fontSize = 70.sp, fontWeight = FontWeight.Bold)
                }
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Button(onClick = {
                        coroutineScope.launch {
                            rollDice()
                            player2Score += currentImg
                            player1turn = true
                        }
                    }, modifier = Modifier.size(100.dp), enabled = !player1turn,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFe67e22)
                        )
                    ) {
                        Image(painter = painterResource(id = R.drawable.spiderman), contentDescription = "")
                    }
                    Text(text = "$player2Score", fontSize = 70.sp, fontWeight = FontWeight.Bold)
                }
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
