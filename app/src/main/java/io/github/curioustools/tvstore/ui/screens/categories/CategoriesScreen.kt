package io.github.curioustools.tvstore.ui.screens.categories

import androidx.compose.runtime.Composable
import androidx.tv.material3.Text

@Composable
fun CategoriesScreen(
    onCategoryClick: (categoryId: String) -> Unit,
    onScroll: (isTopBarVisible: Boolean) -> Unit,
) {
    Text("Catgories Screen")

}