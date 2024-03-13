package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

class NavController(
    private val startDestination: String,
    private var backStackScreen: MutableSet<String> = mutableSetOf()
) {
    var currentScreen: MutableState<String> = mutableStateOf(startDestination)
    fun navigate(route: String) {
        if (route != currentScreen.value) {
            if (
                backStackScreen.contains(currentScreen.value)
                && currentScreen.value != startDestination
            ) {
                backStackScreen.remove(currentScreen.value)
            }
            if (route == startDestination) {
                backStackScreen = mutableSetOf()
            } else {
                backStackScreen.add(currentScreen.value)
            }
            currentScreen.value = route
        }
    }

    fun popBackStack() {
        if (backStackScreen.isNotEmpty()) {
            currentScreen.value = backStackScreen.last()
            backStackScreen.remove(currentScreen.value)
        }
    }
}

@Composable
fun rememberNavController(
    startDestination: String,
    backStackScreen: MutableSet<String> = mutableSetOf()
): MutableState<NavController> = rememberSaveable {
    mutableStateOf(NavController(startDestination, backStackScreen))
}