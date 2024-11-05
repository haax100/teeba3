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
class BitronikStentDetailsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val stentName = intent.getStringExtra("stentName") ?: "Unknown Stent"
                val imageUrl = intent.getStringExtra("imageUrl") ?: ""
                val cartViewModel: CartViewModel = viewModel()
                BiotronikStentDetailsScreen(stentName, imageUrl, cartViewModel)
            }
        }
    }
}

@Composable
fun BiotronikStentDetailsScreen(
    stentName: String,
    imageUrl: String,
    cartViewModel: CartViewModel
) {
    val context = LocalContext.current
    val sizes = when (stentName) {
        "PK Papyrus" -> listOf(
            "5F 2.5mm x 20mm", "5F 2.5mm x 26mm", "5F 3.0mm x 15mm", "5F 3.0mm x 20mm", "5F 3.0mm x 26mm",
            "5F 3.5mm x 15mm", "5F 3.5mm x 20mm", "5F 3.5mm x 26mm", "5F 4.0mm x 15mm", "5F 4.0mm x 20mm",
            "5F 4.0mm x 26mm", "6F 4.5mm x 15mm", "6F 4.5mm x 20mm", "6F 4.5mm x 26mm", "6F 5.0mm x 15mm",
            "6F 5.0mm x 20mm"
        )
        "Orsiro" -> listOf(
            "2.25mm x 9mm", "2.25mm x 13mm", "2.25mm x 15mm", "2.25mm x 18mm", "2.25mm x 22mm", "2.25mm x 26mm",
            "2.25mm x 30mm", "2.50mm x 9mm", "2.50mm x 13mm", "2.50mm x 15mm", "2.50mm x 18mm", "2.50mm x 22mm",
            "2.50mm x 26mm", "2.50mm x 30mm", "2.75mm x 9mm", "2.75mm x 13mm", "2.75mm x 15mm", "2.75mm x 18mm",
            "2.75mm x 22mm", "2.75mm x 26mm", "2.75mm x 30mm", "3.00mm x 9mm", "3.00mm x 13mm", "3.00mm x 15mm",
            "3.00mm x 18mm", "3.00mm x 22mm", "3.00mm x 26mm", "3.00mm x 30mm", "4.00mm x 9mm", "4.00mm x 13mm",
            "4.00mm x 15mm", "4.00mm x 18mm", "4.00mm x 22mm", "4.00mm x 26mm", "4.00mm x 30mm"
        )
        "Orsiro Mission DES" -> listOf(
            "2.25mm x 9mm", "2.25mm x 13mm", "2.25mm x 15mm", "2.25mm x 18mm", "2.25mm x 22mm", "2.25mm x 26mm",
            "2.25mm x 30mm", "2.50mm x 9mm", "2.50mm x 13mm", "2.50mm x 15mm", "2.50mm x 18mm", "2.50mm x 22mm",
            "2.50mm x 26mm", "2.50mm x 30mm", "2.50mm x 35mm", "2.50mm x 40mm", "2.75mm x 9mm", "2.75mm x 13mm",
            "2.75mm x 15mm", "2.75mm x 18mm", "2.75mm x 22mm", "2.75mm x 26mm", "2.75mm x 30mm", "2.75mm x 35mm",
            "2.75mm x 40mm", "3.00mm x 9mm", "3.00mm x 13mm", "3.00mm x 15mm", "3.00mm x 18mm", "3.00mm x 22mm",
            "3.00mm x 26mm", "3.00mm x 30mm", "3.00mm x 35mm", "3.00mm x 40mm", "3.50mm x 9mm", "3.50mm x 13mm",
            "3.50mm x 15mm", "3.50mm x 18mm", "3.50mm x 22mm", "3.50mm x 26mm", "3.50mm x 30mm", "3.50mm x 35mm",
            "3.50mm x 40mm", "4.00mm x 9mm", "4.00mm x 13mm", "4.00mm x 15mm", "4.00mm x 18mm", "4.00mm x 22mm",
            "4.00mm x 26mm", "4.00mm x 30mm", "4.00mm x 35mm", "4.00mm x 40mm"
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
            text = "Stent: $stentName",
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
                            productId = "$stentName-$size",
                            productName = "$stentName - $size",
                            companyName = "Biotronik",
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