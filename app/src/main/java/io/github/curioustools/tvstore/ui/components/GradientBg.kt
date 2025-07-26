package io.github.curioustools.tvstore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors

@Composable
fun GradientBg() {
    Box(
        modifier = Modifier.Companion
            .background(
                Brush.Companion.linearGradient(
                    MaterialTheme.localUpdatedColors.pairs.random().toList()
                )
            )
            .fillMaxWidth()
            .height(200.dp)
    )
}