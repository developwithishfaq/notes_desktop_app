package ui.components

import androidx.compose.runtime.Composable
import files_system.FilesHelper
import navigation.NavController
import navigation.NavigationHost
import navigation.composable
import ui.add_note_screen.AddNoteScreen
import ui.home_screen.HomeScreen
import ui.settings_screen.SettingScreen

@Composable
fun CustomNavigationHost(
    navController: NavController,
    filesHelper: FilesHelper = FilesHelper()
) {
    NavigationHost(navController) {
        composable(Screens.HomeScreen.name) {
            HomeScreen(navController,filesHelper)
        }
        composable(Screens.SettingsScreen.name) {
            SettingScreen(navController)
        }
        composable(Screens.AddNoteScreen.name) {
            AddNoteScreen(navController,filesHelper)
        }
    }.build()
}