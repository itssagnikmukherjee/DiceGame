package com.itssagnikmukherjee.dicegamemultiplayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    Column {
        Text(text = "Player 1")
        Image(painter = painterResource(id = R.drawable.dice_1), contentDescription = "")
        Row {
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Player 1")
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Player 2")
            }
        }
    }
}