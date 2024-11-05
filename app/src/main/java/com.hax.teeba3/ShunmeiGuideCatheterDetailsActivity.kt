// الملف: ShunmeiGuideCatheterDetailsActivity.kt

package com.hax.teeba3

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
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
class ShunmeiGuideCatheterDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val catheterName = intent.getStringExtra("catheterName") ?: "Dilatation Catheter (SC) - Shun Loach"
                val cartViewModel: CartViewModel = viewModel()
                ShunmeiGuideCatheterDetailsScreen(catheterName, cartViewModel)
            }
        }
    }
}

@Composable
fun ShunmeiGuideCatheterDetailsScreen(catheterName: String, cartViewModel: CartViewModel) {
    val context = LocalContext.current

    val sizes = listOf(
        "1.00mm x 5mm", "1.00mm x 9mm", "1.00mm x 10mm", "1.00mm x 12mm", "1.00mm x 15mm",
        "2.00mm x 20mm", "2.00mm x 25mm", "2.00mm x 30mm" // أكمل باقي المقاسات حسب الحاجة
    )

    val sizeQuantities = remember { mutableStateMapOf<String, Int>() }
    sizes.forEach { size -> if (!sizeQuantities.containsKey(size)) sizeQuantities[size] = 0 }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Guide Catheter: $catheterName",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            sizes.forEach { size ->
                StentSizeCard(
                    size = size,
                    quantity = sizeQuantities[size] ?: 0,
                    onQuantityChange = { newQty ->
                        sizeQuantities[size] = newQty
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val selectedSizes = sizeQuantities.filter { it.value > 0 }
                if (selectedSizes.isNotEmpty()) {
                    selectedSizes.forEach { (size, qty) ->
                        val cartItem = CartItem(
                            productId = catheterName,
                            productName = catheterName,
                            companyName = "Shunmei",
                            size = size,
                            imageUrl = "https://example.com/image.png",
                            quantity = qty
                        )
                        cartViewModel.addToCart(cartItem)
                    }
                    Toast.makeText(context, "تمت إضافة المنتجات إلى السلة", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
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
