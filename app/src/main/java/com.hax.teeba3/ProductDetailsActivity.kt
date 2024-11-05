package com.hax.teeba3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity : ComponentActivity() {
    private var userRole: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Fetch user role from Firestore
        fetchUserRole { role ->
            userRole = role
            setContent {
                Teeba3Theme {
                    // استلام اسم الشركة واسم المنتج من الـ Intent
                    val company = intent.getStringExtra("company") ?: "Unknown Company"
                    val productName = intent.getStringExtra("productName") ?: "Unknown Product"
                    ProductDetailsScreen(company, productName, userRole)
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
fun ProductDetailsScreen(company: String, productName: String, userRole: String) {
    val details = "Details for $productName by $company"
    val scrollState = rememberScrollState()

    // رسوم متحركة بسيطة لتفاصيل المنتج
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedScale = infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display company name and details with animation
        Text(
            text = details,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .scale(animatedScale.value) // Apply animation
        )
        Spacer(modifier = Modifier.height(16.dp))

        // "Add to Cart" button
        Button(
            onClick = {
                // Add product to cart (implement logic as per project requirements)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "إضافة إلى السلة")
        }

        // Admin-only controls
        if (userRole == "Admin") {
            Spacer(modifier = Modifier.height(16.dp))

            // "Edit Product" button
            Button(
                onClick = {
                    // Navigate to edit product screen
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "تعديل المنتج")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // "Delete Product" button
            Button(
                onClick = {
                    // Confirm deletion dialog or implement delete logic
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "حذف المنتج")
            }
        }
    }
}
