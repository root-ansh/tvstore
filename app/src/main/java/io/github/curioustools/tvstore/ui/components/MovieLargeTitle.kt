package io.github.curioustools.tvstore.ui.components.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors

@Composable
fun MovieLargeTitle(movieTitle: String) {
    val bg = MaterialTheme.colorScheme.surface
    val text = MaterialTheme.localUpdatedColors.white
    Text(
        text = movieTitle,
        style = MaterialTheme.typography.displayMedium.copy(
            fontWeight = FontWeight.Companion.Bold
        ),
        maxLines = 1,
        color = text
    )
}