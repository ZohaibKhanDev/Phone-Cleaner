package com.example.phonecleaner.presentation.ui.screens

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun ShieldWithAnimation() {
    var targetNumber by remember { mutableStateOf(0) }
    val animatedNumber by animateIntAsState(
        targetValue = targetNumber,
        animationSpec = tween(durationMillis = 2000)
    )

    var textOffset by remember { mutableStateOf(0f) }


    LaunchedEffect(Unit) {
        targetNumber = 98
        while (true) {
            delay(30)
            textOffset += 2f
            if (textOffset > 200f) textOffset = 0f
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawCircle(
                    color = Color(0xFF00FF00).copy(alpha = 0.2f),
                    radius = size.width / 3,
                    center = center
                )
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShieldShape {
            BasicText(
                text = animatedNumber.toString(),
                style = TextStyle(fontSize = 48.sp, color = Color.White),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
            )


            BasicText(
                text = "Your system is in good    Your system is in good    ",
                style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(x = -textOffset.dp)
                    .padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun ShieldShape(drawContent: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier
            .size(150.dp)
            .drawBehind {
                val width = size.width
                val height = size.height


                val shieldPath = Path().apply {
                    moveTo(width * 0.5f, 0f)
                    lineTo(width, height * 0.25f)
                    lineTo(width, height * 0.75f)
                    lineTo(width * 0.5f, height)
                    lineTo(0f, height * 0.75f)
                    lineTo(0f, height * 0.25f)
                    close()
                }


                drawPath(
                    path = shieldPath,
                    color = Color(0xFFB2FF59),
                    style = Stroke(width = 4.dp.toPx())
                )


                drawRoundRect(
                    color = Color(0xFFE0F7FA).copy(alpha = 0.5f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(30f, 30f),
                    size = size
                )
            },
        contentAlignment = Alignment.Center
    ) {
        drawContent()
    }
}