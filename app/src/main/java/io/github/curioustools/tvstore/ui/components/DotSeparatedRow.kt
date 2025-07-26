package io.github.curioustools.tvstore.ui.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors

@Composable
fun DotSeparatedRow(
    modifier: Modifier = Modifier.Companion,
    texts: List<String>
) {
    val textC = MaterialTheme.localUpdatedColors.white
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        texts.forEachIndexed { index, text ->
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Companion.Normal
                ),
                color = textC
            )
            if (index != texts.lastIndex) {
                Box(
                    modifier = Modifier.Companion
                        .padding(horizontal = 8.dp)
                        .clip(CircleShape)
                        .background(textC)
                        .size(4.dp)
                )
            }
        }
    }
}