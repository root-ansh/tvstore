package io.github.curioustools.tvstore.ui.components.details

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.Icon
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors

@Composable
fun WatchTrailerButton(
    modifier: Modifier = Modifier.Companion,
    goToMoviePlayer: () -> Unit
) {
    Button(
        colors = ButtonDefaults.colors(focusedContainerColor = MaterialTheme.localUpdatedColors.starYellow),
        onClick = goToMoviePlayer,
        modifier = modifier.padding(top = 24.dp),
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding,
        shape = ButtonDefaults.shape(shape = RoundedCornerShape(4.dp))
    ) {
        Icon(
            imageVector = Icons.Outlined.PlayArrow,
            contentDescription = null
        )
        Spacer(Modifier.Companion.size(8.dp))
        Text(
            text = "Play",
            style = MaterialTheme.typography.titleSmall
        )
    }
}