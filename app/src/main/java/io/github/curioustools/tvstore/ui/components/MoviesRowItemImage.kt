package io.github.curioustools.tvstore.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.api.MovieDTO

@Composable
 fun MoviesRowItemImage(
    movie: MovieDTO,
    showIndexOverImage: Boolean,
    index: Int,
    isVertical: Boolean,
    modifier: Modifier = Modifier.Companion,
) {
    Box(contentAlignment = Alignment.Companion.CenterStart) {
        PosterImage(
            isVertical = isVertical,
            movie = movie,
            modifier = modifier
                .fillMaxWidth()
                .drawWithContent {
                    drawContent()
                    if (showIndexOverImage) {
                        drawRect(
                            color = Color.Companion.Black.copy(
                                alpha = 0.1f
                            )
                        )
                    }
                },
        )
        if (showIndexOverImage) {
            Text(
                modifier = Modifier.Companion.padding(16.dp),
                text = "#${index.inc()}",
                style = MaterialTheme.typography.displayLarge
                    .copy(
                        shadow = Shadow(
                            offset = Offset(0.5f, 0.5f),
                            blurRadius = 5f
                        ),
                        color = Color.Companion.White
                    ),
                fontWeight = FontWeight.Companion.SemiBold
            )
        }
    }
}