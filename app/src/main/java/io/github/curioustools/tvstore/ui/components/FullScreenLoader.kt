package io.github.curioustools.tvstore.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors

@Preview
@Composable
fun FullScreenLoader(
    isVisible: Boolean = true,
    gradientColors: List<Color> = listOf( MaterialTheme.localUpdatedColors.yellow,
        MaterialTheme.localUpdatedColors.starYellow,),
    message: String = "Please Wait...",
) {
    if (isVisible) {
        Box(
            modifier = Modifier.Companion
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Companion.Center
        ) {
            Column(horizontalAlignment = Alignment.Companion.CenterHorizontally) {
                GradientCircularProgressIndicator(
                    size = 72.dp,
                    gradientColors = gradientColors,
                    strokeWidth = 16.dp
                )
                if (message.isNotEmpty()) {
                    Spacer(modifier = Modifier.Companion.height(16.dp))
                    Text(
                        text = message,
                        textAlign = TextAlign.Companion.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            }
        }
    }
}


@Composable
fun GradientCircularProgressIndicator(
    size: Dp,
    gradientColors: List<Color>,
    strokeWidth: Dp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        ), label = ""
    )

    Canvas(modifier = Modifier
        .size(size)
        .rotate(angle)) {
        val stroke = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
        drawArc(
            brush = Brush.sweepGradient(gradientColors),
            startAngle = 0f,
            sweepAngle = 270f,
            useCenter = false,
            style = stroke
        )
    }
}