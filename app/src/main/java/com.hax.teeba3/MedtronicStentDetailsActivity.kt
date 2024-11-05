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
class MedtronicStentDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Teeba3Theme {
                val stentName = intent.getStringExtra("stentName") ?: "Unknown Stent"
                val cartViewModel: CartViewModel = viewModel()
                MedtronicStentDetailsScreen(stentName, cartViewModel)
            }
        }
    }
}

@Composable
fun MedtronicStentDetailsScreen(stentName: String, cartViewModel: CartViewModel) {
    val context = LocalContext.current

    // تحديد القياسات بناءً على نوع الدعامة
    val sizes = when (stentName) {
        "Onyx Frontier" -> listOf(
            "2.00mm x 8mm", "2.00mm x 12mm", "2.00mm x 15mm", "2.00mm x 18mm", "2.00mm x 22mm", "2.00mm x 26mm", "2.00mm x 30mm",
            "2.25mm x 8mm", "2.25mm x 12mm", "2.25mm x 15mm", "2.25mm x 18mm", "2.25mm x 22mm", "2.25mm x 26mm", "2.25mm x 30mm", "2.25mm x 34mm", "2.25mm x 38mm",
            "2.50mm x 8mm", "2.50mm x 12mm", "2.50mm x 15mm", "2.50mm x 18mm", "2.50mm x 22mm", "2.50mm x 26mm", "2.50mm x 30mm", "2.50mm x 34mm", "2.50mm x 38mm",
            "2.75mm x 8mm", "2.75mm x 12mm", "2.75mm x 15mm", "2.75mm x 18mm", "2.75mm x 22mm", "2.75mm x 26mm", "2.75mm x 30mm", "2.75mm x 34mm", "2.75mm x 38mm",
            "3.00mm x 8mm", "3.00mm x 12mm", "3.00mm x 15mm", "3.00mm x 18mm", "3.00mm x 22mm", "3.00mm x 26mm", "3.00mm x 30mm", "3.00mm x 34mm", "3.00mm x 38mm",
            "3.50mm x 8mm", "3.50mm x 12mm", "3.50mm x 15mm", "3.50mm x 18mm", "3.50mm x 22mm", "3.50mm x 26mm", "3.50mm x 30mm", "3.50mm x 34mm", "3.50mm x 38mm",
            "4.00mm x 8mm", "4.00mm x 12mm", "4.00mm x 15mm", "4.00mm x 18mm", "4.00mm x 22mm", "4.00mm x 26mm", "4.00mm x 30mm", "4.00mm x 34mm", "4.00mm x 38mm",
            "4.50mm x 12mm", "4.50mm x 15mm", "4.50mm x 18mm", "4.50mm x 22mm", "4.50mm x 26mm", "4.50mm x 30mm",
            "5.00mm x 12mm", "5.00mm x 15mm", "5.00mm x 18mm", "5.00mm x 22mm", "5.00mm x 26mm", "5.00mm x 30mm"
        )
        "Resolute Integrity" -> listOf(
            "2.25mm x 8mm", "2.25mm x 12mm", "2.25mm x 14mm", "2.25mm x 18mm", "2.25mm x 22mm", "2.25mm x 26mm", "2.25mm x 30mm", "2.25mm x 34mm", "2.25mm x 38mm",
            "2.50mm x 8mm", "2.50mm x 12mm", "2.50mm x 14mm", "2.50mm x 18mm", "2.50mm x 22mm", "2.50mm x 26mm", "2.50mm x 30mm", "2.50mm x 34mm", "2.50mm x 38mm",
            "2.75mm x 8mm", "2.75mm x 12mm", "2.75mm x 14mm", "2.75mm x 18mm", "2.75mm x 22mm", "2.75mm x 26mm", "2.75mm x 30mm", "2.75mm x 34mm", "2.75mm x 38mm",
            "3.00mm x 8mm", "3.00mm x 12mm", "3.00mm x 14mm", "3.00mm x 18mm", "3.00mm x 22mm", "3.00mm x 26mm", "3.00mm x 30mm", "3.00mm x 34mm", "3.00mm x 38mm",
            "3.50mm x 8mm", "3.50mm x 12mm", "3.50mm x 14mm", "3.50mm x 18mm", "3.50mm x 22mm", "3.50mm x 26mm", "3.50mm x 30mm", "3.50mm x 34mm", "3.50mm x 38mm",
            "4.00mm x 8mm", "4.00mm x 12mm", "4.00mm x 14mm", "4.00mm x 18mm", "4.00mm x 22mm", "4.00mm x 26mm", "4.00mm x 30mm", "4.00mm x 34mm", "4.00mm x 38mm"
        )
        "Resolute Onyx DES" -> listOf(
            "2.00mm x 8mm", "2.00mm x 12mm", "2.00mm x 15mm", "2.00mm x 18mm", "2.00mm x 22mm", "2.00mm x 26mm", "2.00mm x 30mm",
            "2.25mm x 8mm", "2.25mm x 12mm", "2.25mm x 15mm", "2.25mm x 18mm", "2.25mm x 22mm", "2.25mm x 26mm", "2.25mm x 30mm", "2.25mm x 34mm", "2.25mm x 38mm",
            "2.50mm x 8mm", "2.50mm x 12mm", "2.50mm x 15mm", "2.50mm x 18mm", "2.50mm x 22mm", "2.50mm x 26mm", "2.50mm x 30mm", "2.50mm x 34mm", "2.50mm x 38mm",
            "2.75mm x 8mm", "2.75mm x 12mm", "2.75mm x 15mm", "2.75mm x 18mm", "2.75mm x 22mm", "2.75mm x 26mm", "2.75mm x 30mm", "2.75mm x 34mm", "2.75mm x 38mm",
            "3.00mm x 8mm", "3.00mm x 12mm", "3.00mm x 15mm", "3.00mm x 18mm", "3.00mm x 22mm", "3.00mm x 26mm", "3.00mm x 30mm", "3.00mm x 34mm", "3.00mm x 38mm",
            "3.50mm x 8mm", "3.50mm x 12mm", "3.50mm x 15mm", "3.50mm x 18mm", "3.50mm x 22mm", "3.50mm x 26mm", "3.50mm x 30mm", "3.50mm x 34mm", "3.50mm x 38mm",
            "4.00mm x 8mm", "4.00mm x 12mm", "4.00mm x 15mm", "4.00mm x 18mm", "4.00mm x 22mm", "4.00mm x 26mm", "4.00mm x 30mm", "4.00mm x 34mm", "4.00mm x 38mm",
            "4.50mm x 12mm", "4.50mm x 15mm", "4.50mm x 18mm", "4.50mm x 22mm", "4.50mm x 26mm", "4.50mm x 30mm",
            "5.00mm x 12mm", "5.00mm x 15mm", "5.00mm x 18mm", "5.00mm x 22mm", "5.00mm x 26mm", "5.00mm x 30mm"
        )
        else -> listOf("Unknown size")
    }

    // حالة لتخزين الكميات لكل قياس
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
            text = "Stent: $stentName",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // عرض القياسات
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

        // زر إضافة إلى السلة
        Button(
            onClick = {
                val selectedSizes = sizeQuantities.filter { it.value > 0 }
                if (selectedSizes.isNotEmpty()) {
                    selectedSizes.forEach { (size, qty) ->
                        val cartItem = CartItem(
                            productId = stentName,
                            productName = stentName,
                            companyName = "Medtronic",
                            size = size,
                            imageUrl = "https://example.com/image.png",
                            quantity = qty
                        )
                        cartViewModel.addToCart(cartItem) // Fixing the parameter
                    }
                    Toast.makeText(context, "تمت إضافة المنتجات إلى السلة", Toast.LENGTH_SHORT).show()
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
