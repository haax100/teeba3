
package com.hax.teeba3

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Cart : Screen("cart")
    data object Contact : Screen("contact")
    data object Products : Screen("products")
}
