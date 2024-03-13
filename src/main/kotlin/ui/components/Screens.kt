package ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screens(
    val label: String,
    val icon: ImageVector,
) {
    HomeScreen(
        label = "Home",
        icon = Icons.Default.Home
    ),
    AddNoteScreen(
        label = "Add",
        icon = Icons.Default.Add
    ),
    SettingsScreen(
        label = "Settings",
        icon = Icons.Default.Settings
    ),

}