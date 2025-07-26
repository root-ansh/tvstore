package io.github.curioustools.tvstore.ui.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import io.github.curioustools.tvstore.ui.utils.rememberChildPadding
import io.github.curioustools.tvstore.ui.utils.localUpdatedColors

@OptIn(ExperimentalComposeUiApi::class) @Composable
fun CastAndCrewList(castAndCrew: List<String>) {
    val childPadding = rememberChildPadding()
    val bg = MaterialTheme.colorScheme.surface
    val text = MaterialTheme.localUpdatedColors.white

    Column(
        modifier = Modifier.Companion.padding(top = childPadding.top).background(bg),
    ) {
        Text(
            text = "Cast and Crew",
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp
            ),
            modifier = Modifier.Companion.padding(start = childPadding.start),
            color = text
        )
        LazyRow(
            modifier = Modifier.Companion
                .padding(top = 16.dp)
                .focusRestorer(),
            contentPadding = PaddingValues(start = childPadding.start)
        ) {
            items(castAndCrew, key = { it }) {
                CastAndCrewItem(it, modifier = Modifier.Companion.width(144.dp))
            }
        }
    }
}