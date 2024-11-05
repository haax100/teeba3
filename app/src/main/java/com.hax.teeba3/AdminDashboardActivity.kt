package com.hax.teeba3


import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminDashboardActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    var onLogoutClick = {
                        startActivity(Intent(this, LoginActivity::class.java))
                        finish() // إنهاء النشاط الحالي لإزالة التواجد في الخلفية
                    }

                }
            }
        }
    }
}

@Composable
fun AdminDashboardScreen(
    onUserManagementClick: () -> Unit,
    onManageProductsClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Admin Dashboard", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onUserManagementClick) {
            Text("User Management")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = onManageProductsClick) {
            Text("Manage Products")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = onLogoutClick) {
            Text("Logout")
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true)
@Composable
fun AdminDashboardPreview() {
    Teeba3Theme {
        AdminDashboardScreen(
            onUserManagementClick = {},
            onManageProductsClick = {},
            onLogoutClick = {}
        )
    }
}
