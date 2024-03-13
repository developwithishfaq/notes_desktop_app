package navigation

import androidx.compose.runtime.Composable

class NavigationHost(
    val navController: NavController,
    val contents: @Composable NavigationBuilder.() -> Unit
) {

    @Composable
    fun build() {
        NavigationBuilder(navController).renderContents()
    }

    inner class NavigationBuilder(
        val navController: NavController = this@NavigationHost.navController
    ) {
        @Composable
        fun renderContents() {
            this@NavigationHost.contents(this)
        }

    }

}


@Composable
fun NavigationHost.NavigationBuilder.composable(
    route: String,
    content: @Composable () -> Unit
) {
    if (navController.currentScreen.value == route) {
        content()
    }
}
