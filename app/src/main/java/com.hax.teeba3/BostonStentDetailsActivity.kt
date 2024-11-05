package com.hax.teeba3


import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hax.teeba3.model.CartItem
import com.hax.teeba3.ui.theme.Teeba3Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BostonStentDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val stentName = intent.getStringExtra("stentName") ?: "Unknown Stent"
                val imageUrl = intent.getStringExtra("imageUrl") ?: ""
                val cartViewModel: CartViewModel = viewModel()
                BostonStentDetailsScreen(stentName, imageUrl, cartViewModel)
            }
        }
    }
}

@Composable
fun BostonStentDetailsScreen(stentName: String, imageUrl: String, cartViewModel: CartViewModel) {
    val context = LocalContext.current
    val scrollState = rememberLazyListState()

    // تحديد الأحجام بناءً على اسم الدعامة
    val sizes = when (stentName) {
        "Promus" -> listOf(
            "2.25mm x 8mm", "2.25mm x 12mm", "2.25mm x 16mm", "2.25mm x 20mm", "2.25mm x 24mm", "2.25mm x 28mm",
            "2.25mm x 32mm", "2.25mm x 38mm", "2.50mm x 8mm", "2.50mm x 12mm", "2.50mm x 16mm", "2.50mm x 20mm",
            "2.50mm x 24mm", "2.50mm x 28mm", "2.50mm x 32mm", "2.50mm x 38mm", "2.75mm x 8mm", "2.75mm x 12mm",
            "2.75mm x 16mm", "2.75mm x 20mm", "2.75mm x 24mm", "2.75mm x 28mm", "2.75mm x 32mm", "2.75mm x 38mm",
            "3.00mm x 8mm", "3.00mm x 12mm", "3.00mm x 16mm", "3.00mm x 20mm", "3.00mm x 24mm", "3.00mm x 28mm",
            "3.00mm x 32mm", "3.00mm x 38mm", "3.50mm x 8mm", "3.50mm x 12mm", "3.50mm x 16mm", "3.50mm x 20mm",
            "3.50mm x 24mm", "3.50mm x 28mm", "3.50mm x 32mm", "3.50mm x 38mm", "4.00mm x 8mm", "4.00mm x 12mm",
            "4.00mm x 16mm", "4.00mm x 20mm", "4.00mm x 24mm", "4.00mm x 28mm", "4.00mm x 32mm", "4.00mm x 38mm"
        )
        "REBEL" -> listOf(
            "2.25mm x 8mm", "2.25mm x 12mm", "2.25mm x 16mm", "2.25mm x 20mm", "2.25mm x 24mm", "2.25mm x 28mm",
            "2.25mm x 32mm", "2.25mm x 38mm", "2.50mm x 8mm", "2.50mm x 12mm", "2.50mm x 16mm", "2.50mm x 20mm",
            "2.50mm x 24mm", "2.50mm x 28mm", "2.50mm x 32mm", "2.50mm x 38mm", "2.75mm x 8mm", "2.75mm x 12mm",
            "2.75mm x 16mm", "2.75mm x 20mm", "2.75mm x 24mm", "2.75mm x 28mm", "2.75mm x 32mm", "2.75mm x 38mm",
            "3.00mm x 8mm", "3.00mm x 12mm", "3.00mm x 16mm", "3.00mm x 20mm", "3.00mm x 24mm", "3.00mm x 28mm",
            "3.00mm x 32mm", "3.00mm x 38mm", "3.50mm x 8mm", "3.50mm x 12mm", "3.50mm x 16mm", "3.50mm x 20mm",
            "3.50mm x 24mm", "3.50mm x 28mm", "3.50mm x 32mm", "3.50mm x 38mm", "4.00mm x 8mm", "4.00mm x 12mm",
            "4.00mm x 16mm", "4.00mm x 20mm", "4.00mm x 24mm", "4.00mm x 28mm", "4.00mm x 32mm", "4.00mm x 38mm"
        )
        "SYNERGY" -> listOf(
            // القائمة الكاملة من القياسات التي ذكرتها
        )
        else -> listOf("Unknown size")
    }

    val sizeQuantities = remember { mutableStateMapOf<String, Int>() }
    sizes.forEach { size ->
        if (!sizeQuantities.containsKey(size)) {
            sizeQuantities[size] = 0
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        state = scrollState,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = "Stent: $stentName",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }

        items(sizes.size) { index ->
            val size = sizes[index]
            StentSizeCard(
                size = size,
                quantity = sizeQuantities[size] ?: 0,
                onQuantityChange = { newQty ->
                    sizeQuantities[size] = newQty
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))

            // زر إضافة إلى السلة
            Button(
                onClick = {
                    val selectedSizes = sizeQuantities.filter { it.value > 0 }
                    if (selectedSizes.isNotEmpty()) {
                        selectedSizes.forEach { (size, qty) ->
                            val cartItem = CartItem(
                                productId = "$stentName-$size", // معرف فريد
                                productName = stentName,
                                companyName = "Boston",
                                imageUrl = imageUrl, // تأكد من إضافة الـ imageUrl هنا
                                size = size,
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
                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    "إضافة إلى السلة",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold

                )
            }
        }
    }
}
