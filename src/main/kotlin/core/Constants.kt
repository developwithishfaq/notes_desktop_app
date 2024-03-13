package core

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun VerticalSpace(dp: Int = 12) {
    Spacer(modifier = Modifier.height(dp.dp))
}

@Composable
fun HorizontalSpace(dp: Int = 12) {
    Spacer(modifier = Modifier.width(dp.dp))
}

fun getCurrentUsername(): String {
    return System.getProperty("user.name")
}
