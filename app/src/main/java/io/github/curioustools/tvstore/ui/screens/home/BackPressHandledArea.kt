package io.github.curioustools.tvstore.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type

@Composable
fun BackPressHandledArea(
    onBackPressed: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) =
    Box(
        modifier = Modifier.Companion.onPreviewKeyEvent {
            if (it.key == Key.Companion.Back && it.type == KeyEventType.Companion.KeyUp) {
                onBackPressed()
                true
            } else false

        },
        content = content
    )