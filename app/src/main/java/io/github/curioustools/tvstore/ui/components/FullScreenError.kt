package io.github.curioustools.tvstore.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.base.FailureInfo
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors

@Preview
@Composable
fun FullScreenError(
    isVisible : Boolean = true,
    info: FailureInfo? = FailureInfo(Throwable(""),200,"Something Went Wrong")
) {
    if (isVisible) {
        Box(
            modifier = Modifier.Companion
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Companion.Center
        ) {
            Column(horizontalAlignment = Alignment.Companion.CenterHorizontally) {
                Icon(
                    Icons.Filled.Warning,
                    modifier = Modifier.Companion
                        .size(72.dp)
                        .padding(4.dp),
                    contentDescription = "",
                    tint = MaterialTheme.localUpdatedColors.alertError
                )
                Text(
                    "Something is not right",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.Companion.fillMaxWidth().padding(horizontal = 16.dp),
                    textAlign = TextAlign.Companion.Center
                )

                Text(
                    info?.message.orEmpty(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.Companion.fillMaxWidth().padding(vertical = 12.dp),
                    textAlign = TextAlign.Companion.Center
                )

            }
        }
    }
}