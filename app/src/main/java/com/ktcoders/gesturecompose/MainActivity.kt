package com.ktcoders.gesturecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ktcoders.gesturecompose.ui.theme.GestureComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestureComposeTheme {

                GestureDetectScreen()

            }
        }
    }
}

@Composable
private fun GestureDetectScreen() {

    val xSpeed = 10
    val ySpeed = 15
    val width = remember { mutableStateOf(0) }
    val volume = remember { mutableStateOf(0) }
    val brightness = remember { mutableStateOf(0) }
    val playerPosition = remember { mutableStateOf(0) }

    Column(
        Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                width.value = it.size.width
            }
            .pointerInput(Unit) {
                detectDragGestures(onDrag = { pointerInputChange, dragAmount ->

                    /* player position */
                    playerPosition.value += (dragAmount.x.toInt() / xSpeed)
                    if (playerPosition.value >= 100) {
                        playerPosition.value = 100
                    } else if (playerPosition.value <= 0) {
                        playerPosition.value = 0
                    }

                    val valueY = -dragAmount.y.toInt() / ySpeed
                    if (pointerInputChange.position.x.toInt() > width.value / 2) {/* change volume, it is right of screen */
                        volume.value += valueY
                        if (volume.value >= 15) {
                            volume.value = 15
                        } else if (volume.value <= 0) {
                            volume.value = 0
                        }
                        volume.value = volume.value
                    } else {/* change brightness, it is left of screen */
                        brightness.value += valueY
                        if (brightness.value >= 15) {
                            brightness.value = 15
                        } else if (brightness.value <= 0) {
                            brightness.value = 0
                        }
                        brightness.value = brightness.value
                    }

                })
            }) {

        Text(text = "volume: ${volume.value}", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "brightness: ${brightness.value}", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = "playerPosition: ${playerPosition.value}", fontSize = 24.sp)

    }
}