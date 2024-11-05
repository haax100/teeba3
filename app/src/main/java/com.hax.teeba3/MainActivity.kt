
package com.hax.teeba3

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val cartViewModel: CartViewModel by viewModels()
    private var userRole: String = ""

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fetch user role from Firestore
        fetchUserRole { role ->
            userRole = role
            setContent {
                Teeba3Theme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainScreen(cartViewModel, userRole)
                    }
                }
            }
        }
    }

    private fun fetchUserRole(onRoleFetched: (String) -> Unit) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        userId?.let { uid ->
            db.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    val role = document.getString("role") ?: "Customer"
                    onRoleFetched(role)
                }
                .addOnFailureListener {
                    onRoleFetched("Customer") // Default role if fetching fails
                }
        }
    }
}

@Composable
fun MainScreen(cartViewModel: CartViewModel, userRole: String) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            CustomHeaderBar {
                navController.navigate(Screen.Cart.route)
            }
        },
        bottomBar = {
            BottomNavigationBar(navController, userRole)
        },
        content = { innerPadding ->
            NavGraph(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                userRole = userRole
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomHeaderBar(onCartClick: () -> Unit) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Fit
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* Open Drawer */ }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = onCartClick) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun BottomNavigationBar(navController: NavController, userRole: String) {
    // Define different navigation items based on user role
    val items = if (userRole == "Admin") {
        listOf(
            BottomNavItem("Home", "home", Icons.Default.Home),
            BottomNavItem("Products", "products", Icons.Default.ShoppingCart),
            BottomNavItem("Contact", "contact", Icons.Default.Email),
            BottomNavItem("Admin", "admin_dashboard", Icons.Default.Menu) // Only for Admin
        )
    } else {
        listOf(
            BottomNavItem("Home", "home", Icons.Default.Home),
            BottomNavItem("Products", "products", Icons.Default.ShoppingCart),
            BottomNavItem("Contact", "contact", Icons.Default.Email)
        )
    }

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.title)
                },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val title: String, val route: String, val icon: ImageVector)
