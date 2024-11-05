package com.hax.teeba3

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsActivity : ComponentActivity() {

    private var userRole: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fetch user role from Firestore
        fetchUserRole { role ->
            userRole = role
            setContent {
                Teeba3Theme {
                    ProductsCategoriesScreen(userRole)
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
fun ProductsCategoriesScreen(userRole: String) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Display "Add Product" button for Admins only
        if (userRole == "Admin") {
            Button(
                onClick = {
                    // Navigate to Add Product Screen
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("إضافة منتج")
            }
            Spacer(modifier = Modifier.size(16.dp))
        }

        // Display Product Categories
        SectionCard(
            title = "Coronary Intervention",
            icon = R.drawable.ic_coronary,
            context = context,
            backgroundColor = Color.Cyan
        )
        SectionCard(
            title = "Peripheral Intervention",
            icon = R.drawable.ic_peripheral,
            context = context,
            backgroundColor = Color.Magenta
        )
    }
}

@Composable
fun SectionCard(title: String, icon: Int, context: android.content.Context, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (title == "Coronary Intervention") {
                    val intent = Intent(context, CoronaryProductsActivity::class.java)
                    context.startActivity(intent)
                }
            },
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .background(backgroundColor)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
            )
            Spacer(modifier = Modifier.width(24.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
