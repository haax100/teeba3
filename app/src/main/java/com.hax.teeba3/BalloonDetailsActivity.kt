package com.hax.teeba3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hax.teeba3.model.CartItem
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BalloonDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val balloonName = intent.getStringExtra("balloonName") ?: "Unknown Balloon"
                val imageUrl = intent.getStringExtra("imageUrl") ?: ""
                val cartViewModel: CartViewModel = viewModel()
                BalloonDetailsScreen(balloonName, imageUrl, cartViewModel)
            }
        }
    }
}

@Composable
fun BalloonDetailsScreen(balloonName: String, imageUrl: String, cartViewModel: CartViewModel) {
    val context = LocalContext.current

    // تحديد القياسات بناءً على نوع البالون
    val sizes = when (balloonName) {
        "Balloon EMERGE" -> listOf(
            "1.20mm x 8mm", "1.20mm x 12mm", "1.20mm x 15mm", "1.20mm x 20mm", "1.20mm x 30mm",
            "1.50mm x 8mm", "1.50mm x 12mm", "1.50mm x 15mm", "1.50mm x 20mm", "1.50mm x 30mm",
            "2.00mm x 8mm", "2.00mm x 12mm", "2.00mm x 15mm", "2.00mm x 20mm", "2.00mm x 30mm",
            "2.25mm x 8mm", "2.25mm x 12mm", "2.25mm x 15mm", "2.25mm x 20mm", "2.25mm x 30mm",
            "2.50mm x 8mm", "2.50mm x 12mm", "2.50mm x 15mm", "2.50mm x 20mm", "2.50mm x 30mm",
            "2.75mm x 8mm", "2.75mm x 12mm", "2.75mm x 15mm", "2.75mm x 20mm", "2.75mm x 30mm",
            "3.00mm x 8mm", "3.00mm x 12mm", "3.00mm x 15mm", "3.00mm x 20mm", "3.00mm x 30mm",
            "3.25mm x 8mm", "3.25mm x 12mm", "3.25mm x 15mm", "3.25mm x 20mm", "3.25mm x 30mm",
            "3.50mm x 8mm", "3.50mm x 12mm", "3.50mm x 15mm", "3.50mm x 20mm", "3.50mm x 30mm",
            "3.75mm x 8mm", "3.75mm x 12mm", "3.75mm x 15mm", "3.75mm x 20mm", "3.75mm x 30mm",
            "4.00mm x 8mm", "4.00mm x 12mm", "4.00mm x 15mm", "4.00mm x 20mm", "4.00mm x 30mm"
        )
        "Balloon Maverick2" -> listOf(
            "1.50mm x 9mm", "1.50mm x 12mm", "1.50mm x 15mm", "1.50mm x 20mm", "1.50mm x 25mm", "1.50mm x 30mm",
            "2.00mm x 9mm", "2.00mm x 12mm", "2.00mm x 15mm", "2.00mm x 20mm", "2.00mm x 25mm", "2.00mm x 30mm",
            "2.25mm x 9mm", "2.25mm x 12mm", "2.25mm x 15mm", "2.25mm x 20mm", "2.25mm x 25mm", "2.25mm x 30mm",
            "2.50mm x 9mm", "2.50mm x 12mm", "2.50mm x 15mm", "2.50mm x 20mm", "2.50mm x 25mm", "2.50mm x 30mm",
            "2.75mm x 9mm", "2.75mm x 12mm", "2.75mm x 15mm", "2.75mm x 20mm", "2.75mm x 25mm", "2.75mm x 30mm",
            "3.00mm x 9mm", "3.00mm x 12mm", "3.00mm x 15mm", "3.00mm x 20mm", "3.00mm x 25mm", "3.00mm x 30mm",
            "3.25mm x 9mm", "3.25mm x 12mm", "3.25mm x 15mm", "3.25mm x 20mm", "3.25mm x 25mm", "3.25mm x 30mm",
            "3.50mm x 9mm", "3.50mm x 12mm", "3.50mm x 15mm", "3.50mm x 20mm", "3.50mm x 25mm", "3.50mm x 30mm"
        )
        "Balloon NC EMERGE" -> listOf(
            "1.20mm x 6mm", "1.20mm x 8mm", "1.20mm x 12mm", "1.20mm x 15mm", "1.20mm x 20mm", "1.20mm x 30mm",
            "1.50mm x 6mm", "1.50mm x 8mm", "1.50mm x 12mm", "1.50mm x 15mm", "1.50mm x 20mm", "1.50mm x 30mm"
        )
        "Balloon NC QUANTUM" -> listOf(
            "1.20mm x 6mm", "1.20mm x 8mm", "1.20mm x 12mm", "1.20mm x 15mm", "1.20mm x 20mm", "1.20mm x 30mm",
            "1.50mm x 6mm", "1.50mm x 8mm", "1.50mm x 12mm", "1.50mm x 15mm", "1.50mm x 20mm", "1.50mm x 30mm"
        )
        else -> listOf("Unknown size")
    }
    val sizeQuantities = remember { mutableStateMapOf<String, Int>() }
    sizes.forEach { size -> sizeQuantities.putIfAbsent(size, 0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Balloon: $balloonName",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        sizes.forEach { size ->
            StentSizeCard(
                size = size,
                quantity = sizeQuantities[size] ?: 0,
                onQuantityChange = { newQty -> sizeQuantities[size] = newQty }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val selectedSizes = sizeQuantities.filter { it.value > 0 }
                if (selectedSizes.isNotEmpty()) {
                    selectedSizes.forEach { (size, qty) ->
                        val cartItem = CartItem(
                            productId = "$balloonName-$size",
                            productName = balloonName,
                            companyName = "Abbott",
                            size = size, // تمرير الـ size هنا
                            imageUrl = imageUrl,
                            quantity = qty
                        )
                        cartViewModel.addToCart(cartItem)
                    }
                    Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(
                "إضافة إلى السلة",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
