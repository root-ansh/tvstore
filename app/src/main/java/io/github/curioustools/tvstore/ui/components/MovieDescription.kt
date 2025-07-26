package io.github.curioustools.tvstore.ui.components.details

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors

@Composable
fun MovieDescription(description: String) {
    val bg = MaterialTheme.colorScheme.surface
    val text = MaterialTheme.localUpdatedColors.white
    Text(
        text = description,
        style = MaterialTheme.typography.titleSmall.copy(
            fontSize = 18.sp,
            fontWeight = FontWeight.Companion.Bold
        ),
        modifier = Modifier.Companion.padding(top = 8.dp),
        color = text
    )
}