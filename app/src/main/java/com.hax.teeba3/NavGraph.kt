package com.hax.teeba3

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    userRole: String
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val cartViewModel: CartViewModel = hiltViewModel()
            HomeScreen(
                cartViewModel = cartViewModel
            )
        }
        composable(Screen.Products.route) {
            ProductsCategoriesScreen(userRole = userRole)
        }
        composable(Screen.Contact.route) {
            ContactScreen()
        }
        composable(Screen.Cart.route) {
            val cartViewModel: CartViewModel = hiltViewModel()
            val context = LocalContext.current
            CartScreen(
                cartViewModel = cartViewModel,
                onPrintOrder = { selectedSizes ->
                    val stentName = selectedSizes.firstOrNull()?.productName ?: "Unknown Product"
                    printOrder(context, stentName, selectedSizes)
                }
            )
        }
    }
}
