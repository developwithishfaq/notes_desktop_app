import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.singleWindowApplication
import files_system.FilesHelper
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import navigation.rememberNavController
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.get
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import ui.components.CustomNavigationHost
import ui.components.Screens
import java.io.File

@Composable
@Preview
fun App() {
    MaterialTheme {

        val navController by rememberNavController(Screens.HomeScreen.name)

        val currentScreen by remember {
            navController.currentScreen
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NavigationRail {
                Screens.entries.forEach {
                    NavigationRailItem(
                        selected = currentScreen == it.name,
                        icon = {
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.label
                            )
                        },
                        onClick = {
                            navController.navigate(it.name)
                        }
                    )
                }
            }
            CustomNavigationHost(navController)
        }
    }
}


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
