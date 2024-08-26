package com.example.phonecleaner.presentation.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay


@Composable
fun AnimatedShield(
    score: Int,
    isAnimating: Boolean,
    animationFinished: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition()
    val animatedProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )


    val waterAnimationValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val targetScore = if (isAnimating) score else 0
    val animatedScore by animateIntAsState(
        targetValue = targetScore,
        animationSpec = tween(durationMillis = 500)
    ) {
        if (targetScore == score) {
            animationFinished()
        }
    }
    ShieldView(
        score = animatedScore,
        animatedProgress = animatedProgress,
        waterAnimationValue = waterAnimationValue
    )
}

@Composable
fun ShieldView(
    score: Int,
    animatedProgress: Float,
    waterAnimationValue: Float
) {
    val shieldShape = GenericShape { size: Size, layoutDirection ->
        val width = size.width
        val height = size.height
        val curveSize = width * 0.2f + waterAnimationValue

        moveTo(width / 2, 0f)
        cubicTo(
            x1 = width - curveSize, y1 = 0f,
            x2 = width, y2 = curveSize,
            x3 = width, y3 = height / 2
        )
        cubicTo(
            x1 = width, y1 = height - curveSize,
            x2 = width - curveSize, y2 = height,
            x3 = width / 2, y3 = height
        )
        cubicTo(
            x1 = curveSize, y1 = height,
            x2 = 0f, y2 = height - curveSize,
            x3 = 0f, y3 = height / 2
        )
        cubicTo(
            x1 = 0f, y1 = curveSize,
            x2 = curveSize, y2 = 0f,
            x3 = width / 2, y3 = 0f
        )
        close()
    }

    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(shieldShape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFE0FFFF),
                        Color(0xFF90EE90)
                    ),
                    start = Offset.Zero,
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
            .shadow(elevation = 8.dp, shape = shieldShape),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$score",
                color = Color(0xFF008000),
                fontSize = 48.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            AnimatingText(text = " Your system is in good ", fontSize = 14.sp)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.3f * animatedProgress),
                            Color.Transparent
                        )
                    )
                )
        )
    }
}

@Composable
fun AnimatingText(
    text: String,
    fontSize: TextUnit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(key1 = text) {
        while (true) {
            scrollState.scrollTo(0)
            scrollState.animateScrollTo(
                scrollState.maxValue,
                animationSpec = tween(durationMillis = 6000, easing = LinearEasing)
            )
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState, reverseScrolling = false),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = text,
            color = Color(0xFF008000),
            fontSize = fontSize,
            modifier = Modifier.padding(end = 100.dp)
        )

        Text(
            text = text,
            color = Color(0xFF008000),
            fontSize = fontSize,
            modifier = Modifier.padding(end = 100.dp)
        )
    }
}
